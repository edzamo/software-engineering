# MapStruct (Spring Boot) — Cuándo y cómo usarlo

MapStruct genera en compilación los mapeos entre objetos. En arquitectura hexagonal es **un detalle de infraestructura**: solo vive en `adapter.in` (DTO ↔ dominio) y `adapter.out` (entidad de persistencia ↔ dominio). Nunca en `domain` ni `application`.

## 1. Decisión: manual vs MapStruct
Empieza **manual** (mappers estáticos + test de ida y vuelta, INV-11). Pasa a MapStruct cuando se cumpla **alguna** de estas condiciones y el arch-validator lo apruebe en la Fase 1:

| Señal | Umbral orientativo |
|-------|--------------------|
| Agregados/DTOs con mapeo | 4 o más pares de tipos |
| Campos planos por tipo (mismo nombre en ambos lados) | 8 o más de media. Medido: con 15 campos planos el mapper pasa de 45 a 12 líneas; con menos de 8 el ahorro es nulo o negativo |
| Mappers manuales repetitivos (`get`→`set`) | Más de ~150 líneas en total |
| Se necesita mapeo de colecciones/anidados en varios sitios | Sí |

**No lo uses** si el dominio es inmutable con constructor privado y fábricas y el mapeo es pequeño: obliga a métodos `default` escritos a mano y añade complejidad de build (procesador de anotaciones + Lombok) sin ahorrar código.

**Medido en coffee-shop** (3 agregados, ~4 campos por tipo, dominio inmutable, prueba real con MapStruct 1.6.3): +23 líneas escritas a mano y 565 generadas; sin ahorro. Lo único que aporta son las políticas de compilación (`ERROR`), que además se pueden cubrir con tests (INV-11). El ahorro crece con los **campos planos por tipo**, no con el número de agregados.

Quien propone adoptarlo declara en la propuesta de diseño: pares de tipos, cuánto código elimina y cómo se cubren los mapeos con dominio inmutable.

## 2. Dependencias (Gradle)
Versión verificada: **1.6.3** (Spring Boot 3.5.4, Java 17, Lombok 1.18.x). Comprueba la estable vigente al adoptarlo. `lombok-mapstruct-binding` no fue imprescindible en la prueba (compiló con y sin él, usando el builder de Lombok); se mantiene por recomendación oficial.
```groovy
dependencies {
    implementation 'org.mapstruct:mapstruct:<version>'
    annotationProcessor 'org.mapstruct:mapstruct-processor:<version>'
    // Con Lombok (solo en infraestructura), el binding va ENTRE ambos y Lombok primero:
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
}
tasks.withType(JavaCompile).configureEach {
    options.compilerArgs += ['-Amapstruct.defaultComponentModel=spring',
                             '-Amapstruct.unmappedTargetPolicy=ERROR',
                             '-Amapstruct.defaultInjectionStrategy=constructor']
}
```
Maven equivalente: `annotationProcessorPaths` con lombok, `lombok-mapstruct-binding` y `mapstruct-processor`, en ese orden.

## 3. Reglas (MS-01…MS-10)
- **MS-01** Ubicación: interfaces `@Mapper` en `adapter.in.*` / `adapter.out.persistence`. `org.mapstruct` prohibido en `domain` y `application` (regla ArchUnit obligatoria al adoptarlo).
- **MS-02** `unmappedTargetPolicy = ERROR`: un campo destino sin origen rompe la compilación. **Solo vigila el destino:** un campo nuevo del dominio (getter) sin destino en la entidad se pierde en silencio y compila; mantén el test de ida y vuelta (INV-11) para detectarlo. `unmappedSourcePolicy=ERROR` no es viable (las entidades tienen `id`, `version`… sin origen). Los campos que se ignoran a propósito se declaran con `@Mapping(target = "x", ignore = true)` y comentario del motivo.
- **MS-03** `componentModel = "spring"` con inyección por **constructor**. Nada de `Mappers.getMapper` en producción (sí en tests unitarios del mapper). Los tests de slice (`@DataJpaTest`, `@WebMvcTest`) no ven el mapper: hay que hacer `@Import(<Mapper>Impl.class)`, si no falla con `NoSuchBeanDefinitionException`.
- **MS-04** Sin lógica de negocio en el mapper: no calcula totales, no genera IDs ni lee el reloj (INV-13). El ID nace en el dominio; el mapper solo lo copia.
- **MS-05** Dominio inmutable: mapea **hacia** el dominio con un método `default` que llame a la fábrica de rehidratación (`Order.rehydrate(...)`). **Prohibido `@ObjectFactory`** con un tipo cuya colección se expone por getter: MapStruct genera `getItems().addAll(...)`, que compila sin aviso y falla en runtime con `UnsupportedOperationException` sobre la lista inmutable (17 de 39 tests fallaron en la prueba). Los invariantes del dominio se ejecutan al rehidratar; el `try/catch` que traduce `InvalidOrderException` a excepción de persistencia (fila corrupta → 500, no 4xx) va dentro de ese método `default`.
- **MS-06** Relaciones bidireccionales JPA (hijo→padre): se completan con `@AfterMapping` o un método `default`; el mapper no debe dejar el hijo sin padre.
- **MS-07** Enums: mapea por nombre (por defecto). Un valor de origen sin destino falla en compilación, pero **un valor que solo existe en el enum destino (JPA) pasa sin error**: mantén el test de exhaustividad entre dominio y persistencia. Cada enum en las dos direcciones son 2 firmas y un `switch` generado; para casos especiales usa `@ValueMapping`, **sin** `MappingConstants.ANY_REMAINING` que colapse estados (INV-11).
- **MS-08** Datos sensibles (INV-17): el PAN viaja `PayRequest → CreditCard` (necesario para validar Luhn); está prohibido que llegue a la entidad, a un DTO de salida o a un log. Se cubre con `unmappedTargetPolicy=ERROR` (un campo `cardNumber` en la entidad rompe la compilación) y un test de reflexión sobre la entidad. `toString` de DTOs/entidades con datos sensibles sigue enmascarado.
- **MS-09** Los mappers **no** inyectan repositorios ni servicios (`uses` solo para otros mappers). Buscar una entidad por ID es responsabilidad del adaptador.
- **MS-10** Un mapper por agregado y por adaptador (`OrderPersistenceMapper`, `OrderWebMapper`); sin mapper "universal".

## 4. Ejemplo (adapter.out, dominio inmutable)
```java
@Mapper // componentModel/inyección/política vienen de los -A del build
interface OrderPersistenceMapper {

    @Mapping(target = "id", ignore = true)          // id técnico lo gestiona JPA
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderDate", ignore = true)   // lo fija el adaptador al crear
    @Mapping(target = "uuid", source = "id")
    // totalAmount se mapea solo desde Order.getCost() (verificado en la prueba; sin expression)
    OrderJpaEntity toEntity(Order order);

    default Order toDomain(OrderJpaEntity e) {      // fábrica del dominio, invariantes intactos
        return Order.rehydrate(e.getUuid(), toDomain(e.getLocation()), toDomainItems(e.getItems()), toDomain(e.getStatus()));
    }

    @AfterMapping
    default void linkItems(@MappingTarget OrderJpaEntity target) {
        target.getItems().forEach(i -> i.setOrder(target));
    }
    // ...enums y LineItem <-> OrderItemJpaEntity
}
```

## 5. Tests (siguen siendo obligatorios)
- **Ida y vuelta por cada valor de enum y de estado** (INV-11): `toEntity` → `toDomain` devuelve un objeto equivalente. MapStruct detecta enums sin mapear al compilar, pero no valida la semántica del mapeo.
- Un test que verifica que campos sensibles no llegan al destino (MS-08).
- Un test de fila corrupta → excepción de persistencia (MS-05).
- Los mappers se prueban unitariamente (`Mappers.getMapper` o instanciando la clase generada), sin arrancar Spring.

## 6. Anti-patrones (bloquean en revisión)
- `@Mapper` importado desde `domain` o `application`.
- `unmappedTargetPolicy = IGNORE` o `WARN`.
- Mapper con `@Autowired` en campo o con repositorios inyectados.
- `expression = "java(UUID.randomUUID())"` o `LocalDateTime.now()` en el mapper.
- `ANY_REMAINING` colapsando estados de dominio en un solo valor de BD.
- Adoptar MapStruct "por costumbre" sin cumplir los umbrales del §1.

## 7. Checklist
- [ ] Umbral del §1 justificado en la propuesta de diseño (Fase 1).
- [ ] Regla ArchUnit: `org.mapstruct..` fuera de `domain` y `application`.
- [ ] `unmappedTargetPolicy=ERROR` activo en el build.
- [ ] Mapeo hacia el dominio pasa por su fábrica/constructor con invariantes.
- [ ] Tests de ida y vuelta por enum/estado y de campos sensibles en verde.
- [ ] Sin lógica de negocio, IDs ni reloj en el mapper.

Quarkus: mismo enfoque con `componentModel = "cdi"` (ver `quarkus/best-practices.md`).
