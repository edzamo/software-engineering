# Spring Security — autenticación y autorización

La pregunta que dispara este documento: *"¿por qué mi endpoint sigue siendo público si agregué `@PreAuthorize`?"* — casi siempre porque falta el filtro que resuelve **quién es** el usuario (autenticación) antes de que la anotación pueda decidir **qué puede hacer** (autorización). Son dos preguntas distintas y Spring Security las separa explícitamente.

Para dónde vive esto dentro de una arquitectura hexagonal (qué capa valida qué) ver [`software-architectures/hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md); para la variante reactiva (`ServerHttpSecurity`) ver [`webflux.md`](webflux.md), sección "Seguridad reactiva" — este documento cubre el modelo clásico (Spring MVC, servlet) y lo que es común a ambos.

## Autenticación vs. autorización — la distinción que nunca hay que mezclar

| | Autenticación | Autorización |
|---|---|---|
| Pregunta que responde | ¿Quién sos? | ¿Qué podés hacer, ya sabiendo quién sos? |
| Mecanismo típico | Validar credenciales (usuario/clave, JWT, sesión) → produce un `Authentication` con el principal. | `hasRole(...)`, `hasAuthority(...)`, `@PreAuthorize` — evalúa el `Authentication` ya resuelto contra una regla. | 
| Falla como | `401 Unauthorized` — no se pudo establecer identidad. | `403 Forbidden` — la identidad es válida, pero no tiene permiso para esta acción. |

Un error común de entrevista: devolver 403 cuando el problema real es que no hay token, o 401 cuando el token es válido pero el rol no alcanza — un entrevistador senior espera que distingas los dos casos y los mapees al status code correcto.

## `SecurityFilterChain` — dónde se conecta todo

```java
@Configuration
@EnableMethodSecurity // habilita @PreAuthorize/@PostAuthorize a nivel de método
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(CsrfConfigurer::disable) // API stateless con JWT — no hay sesión que un CSRF pueda secuestrar
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health", "/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/appointments/**").hasAuthority("SCOPE_appointments:read")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
    }
}
```

- `SecurityFilterChain` (servlet, Spring MVC) es el equivalente exacto de `SecurityWebFilterChain` (reactivo, WebFlux — ver [`webflux.md`](webflux.md)) — mismo modelo mental, distinta API porque una es basada en `HttpServletRequest` y la otra en `ServerWebExchange`.
- **`csrf().disable()` es correcto acá, no una vulnerabilidad** — CSRF protege contra un ataque que abusa de **cookies de sesión** enviadas automáticamente por el navegador; una API stateless que autentica con `Authorization: Bearer <jwt>` en cada request no tiene esa superficie de ataque. Deshabilitarlo en una app que sí usa sesiones/cookies (un login clásico con `JSESSIONID`) sí sería un error de seguridad real — la decisión depende del mecanismo de autenticación, no es "siempre apagarlo".
- `SessionCreationPolicy.STATELESS` — ninguna sesión de servidor; toda la identidad viaja en el JWT de cada request, lo que permite escalar horizontalmente sin *sticky sessions*.

## OAuth2 Resource Server — validar un JWT sin manejar contraseñas

La app **no valida usuario/clave** — eso lo hace un Identity Provider externo (Auth0, Keycloak, Cognito). La app solo actúa como *Resource Server*: recibe un JWT ya emitido y **valida su firma y claims**.

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://tu-dominio.auth0.com/
```

- Con `issuer-uri` configurado, Spring Boot descubre automáticamente el endpoint de claves públicas del Identity Provider (`.well-known/jwks.json`) y valida la firma del JWT sin que se necesite copiar una clave a mano.
- Los **scopes**/**roles** del JWT (claims como `scope` o `permissions`, según cómo los emita el proveedor) se traducen a `GrantedAuthority` — de ahí `hasAuthority("SCOPE_appointments:read")` en el ejemplo de arriba. El prefijo `SCOPE_` es el que agrega Spring Security por defecto al mapear claims de scope.
- **Frase para entrevista:** "la app nunca ve ni guarda contraseñas — confía en el JWT firmado por el Identity Provider, y su único trabajo es verificar esa firma y leer los claims para decidir autorización."

## Method Security — `@PreAuthorize` cerca de la regla de negocio

```java
@PreAuthorize("hasAuthority('SCOPE_appointments:write') and #patientId == authentication.principal.claims['patient_id']")
public Mono<Appointment> reschedule(UUID patientId, RescheduleCommand command) { ... }
```

- Se evalúa **antes** de ejecutar el método — si falla, el método ni se invoca.
- Permite reglas más finas que un simple rol (`hasRole('ADMIN')`) — acá, además del scope, se verifica que el `patientId` del request coincida con el dueño del token (evita que un paciente autenticado reagende el turno de otro paciente solo cambiando un ID en la URL — un caso real de **Broken Object Level Authorization**, el #1 del OWASP API Security Top 10).
- Dónde vive esta anotación en una arquitectura hexagonal es una decisión de diseño real: ponerla en el `@RestController` (adaptador de entrada) es lo más común — la autorización es un detalle de cómo se expone el caso de uso vía HTTP, no una regla de negocio del dominio. Un caso de uso invocado desde un Job de Spring Batch (ver [`spring-batch.md`](spring-batch.md)), por ejemplo, no pasa por este filtro en absoluto — la autorización HTTP no debería filtrarse a `application`/`domain`.

## Password encoding — solo si la app maneja login propio

Si el rol pide autenticación propia (no delegada a un IdP externo), nunca guardar contraseñas en texto plano ni con hashes reversibles (MD5, SHA-256 solo):

```java
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // o Argon2PasswordEncoder — cómputo lento a propósito, resistente a fuerza bruta/rainbow tables
}
```

`BCryptPasswordEncoder`/`Argon2PasswordEncoder` son deliberadamente lentos (a diferencia de un hash criptográfico rápido como SHA-256) — esa lentitud es la defensa contra ataques de fuerza bruta offline si la base de datos de contraseñas se filtra.

## CORS — el error de configuración más repetido

```java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("https://app.saludtools.com")); // nunca "*" en producción con credenciales
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
```

`allowedOrigins("*")` combinado con `allowCredentials(true)` es en realidad **rechazado por el propio navegador** (la spec de CORS lo prohíbe) — pero configurarlo así en un entorno donde no se necesitan credenciales sigue siendo una mala práctica que expone la API a cualquier origen. La regla simple: listar explícitamente los orígenes permitidos, nunca el comodín, en cualquier API que no sea deliberadamente pública y anónima.

## Drills de repaso

| Tiempo | Pregunta |
|---|---|
| 4 min | "Un endpoint devuelve 403 pero el usuario no envió ningún token. ¿Qué debería devolver en realidad, y por qué?" |
| 5 min | "¿Por qué es correcto deshabilitar CSRF en una API stateless con JWT, pero no en una app con sesión de servidor?" |
| 5 min | "Explicá qué es Broken Object Level Authorization con un ejemplo de un endpoint REST, y cómo `@PreAuthorize` puede prevenirlo." |
| 4 min | "¿Por qué la app nunca valida usuario/clave directamente si el login pasa por Auth0/Keycloak?" |

## Referencias

- [Spring Security — Reference Documentation](https://docs.spring.io/spring-security/reference/) — `SecurityFilterChain`, OAuth2 Resource Server, method security.
- [OWASP API Security Top 10](https://owasp.org/API-Security/editions/2023/en/0x00-header/) — Broken Object Level Authorization (API1) y Broken Authentication (API2), los dos riesgos que más se solapan con este documento.

Relacionado: [`webflux.md`](webflux.md) para la variante reactiva (`ServerHttpSecurity`), [`microservices-patterns/README.md`](../../microservices-patterns/README.md) para la tabla OWASP Top 10 general aplicada a microservicios, y [`../nestjs/auth.md`](../nestjs/auth.md) para el mismo problema (JWT, Auth0, guards) resuelto del lado de NestJS.
