# Persistencia en NestJS — TypeORM y Prisma

La pregunta que dispara este documento: *"¿por qué mi entidad TypeORM tiene decoradores `@Entity`/`@Column` si se supone que el dominio no debe conocer el framework?"* — misma tensión que JPA/Hibernate en Spring (ver [`../spring-boot/spring-data.md`](../spring-boot/spring-data.md)): la entidad de persistencia y la entidad de dominio son conceptos distintos, y en TypeScript la confusión es igual de fácil si no se separan explícitamente.

## TypeORM vs Prisma — la pregunta de entrevista más frecuente en Node

| | TypeORM | Prisma |
|---|---|---|
| Estilo | Active Record u opcional Data Mapper — clases decoradas (`@Entity`, `@Column`) con Repository pattern encima. | Schema-first — un archivo `schema.prisma` genera un cliente tipado (`PrismaClient`); no hay clases de entidad decoradas. |
| Migraciones | `typeorm migration:generate`/`run` — genera SQL a partir del diff de entidades. | `prisma migrate dev` — genera SQL a partir del diff del schema declarativo. |
| Tipado de queries | Repository con métodos genéricos (`find`, `findOneBy`) — menos estricto en queries complejas. | `PrismaClient` genera tipos exactos por modelo — autocompletado más preciso, incluso en relaciones anidadas. |
| Madurez/uso típico | Más años en el ecosistema Nest, integración `@nestjs/typeorm` de primera clase. | Más popular en proyectos nuevos por el schema declarativo y el cliente generado — integración vía `@nestjs/prisma` (comunidad) o uso directo del `PrismaClient` como Provider. |

**Frase para entrevista:** "TypeORM se siente más parecido a Hibernate/JPA — entidades decoradas, Repository, Unit of Work opcional. Prisma es un enfoque distinto: el schema es la fuente de verdad y el cliente se genera a partir de él, con tipado más estricto pero menos 'magia' de ORM clásico (no hay lazy-loading transparente, por ejemplo)."

## TypeORM — Entity + Repository

```typescript
@Entity('appointments')
export class AppointmentEntity {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ type: 'uuid' })
  patientId: string;

  @Column({ type: 'enum', enum: AppointmentStatus, default: AppointmentStatus.REQUESTED })
  status: AppointmentStatus;

  @Column({ type: 'timestamptz' })
  scheduledAt: Date;
}

@Injectable()
export class AppointmentsRepository {
  constructor(
    @InjectRepository(AppointmentEntity)
    private readonly repo: Repository<AppointmentEntity>,
  ) {}

  findById(id: string): Promise<AppointmentEntity | null> {
    return this.repo.findOneBy({ id });
  }

  findByPatientAndStatus(patientId: string, status: AppointmentStatus): Promise<AppointmentEntity[]> {
    return this.repo.find({ where: { patientId, status }, order: { scheduledAt: 'ASC' } });
  }
}
```

- `@InjectRepository(AppointmentEntity)` es como Nest inyecta el `Repository<T>` genérico de TypeORM ya configurado para esa entidad — equivalente al proxy dinámico que genera Spring Data para una interfaz `JpaRepository`.
- La `AppointmentEntity` con decoradores **no debería ser la misma clase** que el modelo de dominio si el proyecto sigue un layout hexagonal (ver [`../../software-architectures/hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md), variante NestJS/TS: `domain/model`, `application/{in,out,service}`, `infrastructure/{config,adapter/{in,out}}`) — un `mapper` explícito traduce `AppointmentEntity` (persistencia) ↔ `Appointment` (dominio), igual regla que `OrderJpaEntity` vs `Order` en Spring.

## Prisma — schema declarativo + cliente generado

```prisma
// schema.prisma
model Appointment {
  id          String            @id @default(uuid())
  patientId   String
  status      AppointmentStatus @default(REQUESTED)
  scheduledAt DateTime
}

enum AppointmentStatus {
  REQUESTED
  CONFIRMED
  CANCELLED
}
```

```typescript
@Injectable()
export class AppointmentsRepository {
  constructor(private readonly prisma: PrismaService) {} // PrismaService envuelve PrismaClient como Provider

  findById(id: string) {
    return this.prisma.appointment.findUnique({ where: { id } });
  }

  findByPatientAndStatus(patientId: string, status: AppointmentStatus) {
    return this.prisma.appointment.findMany({
      where: { patientId, status },
      orderBy: { scheduledAt: 'asc' },
    });
  }
}
```

- No hay clase `AppointmentEntity` decorada — el tipo `Appointment` que expone `PrismaClient` se genera automáticamente a partir del `schema.prisma` en build time. Igual conviene **no** devolverlo directo desde el Service hacia el Controller sin pasar por un mapper/DTO propio, por la misma razón que con TypeORM: acoplar el dominio/response HTTP a la forma exacta que Prisma decida generar es frágil ante un cambio de schema.

## El problema N+1 — el mismo problema, otro ORM

```typescript
const appointments = await this.repo.find(); // 1 query
for (const a of appointments) {
  await a.patient; // TypeORM: 1 query MÁS por fila, si la relación es lazy
}
```

Igual que en JPA (ver [`../spring-boot/spring-data.md`](../spring-boot/spring-data.md)), la solución es traer la relación en la **misma** query:

```typescript
// TypeORM — eager join explícito
this.repo.find({ relations: { patient: true } });
// o con QueryBuilder: .leftJoinAndSelect('appointment.patient', 'patient')

// Prisma — include declarativo
this.prisma.appointment.findMany({ include: { patient: true } });
```

Prisma no tiene lazy-loading en absoluto (todas las relaciones se traen explícitamente con `include`/`select`) — elimina la trampa por diseño, al costo de tener que ser explícito siempre, incluso cuando sí se quiere lazy-load bajo demanda.

## Migraciones — el gap más común entre demo y producción

Ambos ORMs tienen su mecanismo de migraciones versionadas, pero el error frecuente en Nest es depender de `synchronize: true` (TypeORM) — que auto-sincroniza el schema de la base de datos con las entidades en cada arranque — **fuera** de desarrollo local. En cualquier ambiente compartido (staging, producción), `synchronize: true` puede alterar o borrar columnas sin control ni historial — el equivalente a confiar en `hibernate.ddl-auto=update` en producción, un antipatrón igual de conocido del lado Java. La regla: migraciones explícitas y versionadas (`typeorm migration:run` / `prisma migrate deploy`) en cualquier ambiente que no sea la laptop del desarrollador.

## Drills de repaso

| Tiempo | Pregunta |
|---|---|
| 4 min | "¿Cuál es la diferencia de enfoque entre TypeORM y Prisma en cómo se define el modelo de datos?" |
| 5 min | "¿Por qué Prisma no tiene el problema clásico de N+1 por lazy-loading no controlado?" |
| 4 min | "¿Por qué `synchronize: true` de TypeORM es peligroso fuera de desarrollo local?" |
| 4 min | "¿Debería `AppointmentEntity` (TypeORM) ser la misma clase que el modelo de dominio? Justificá." |

## Referencias

- [TypeORM — Documentation](https://typeorm.io/) — entidades, repositorios, migraciones, relaciones.
- [Prisma — Documentation](https://www.prisma.io/docs) — schema, cliente generado, migraciones.
- [NestJS — Database recipes (TypeORM)](https://docs.nestjs.com/recipes/sql-typeorm) y [Prisma](https://docs.nestjs.com/recipes/prisma) — integración oficial con Nest.

Relacionado: [`fundamentals.md`](fundamentals.md) para cómo se registra un Repository como Provider inyectable, [`../spring-boot/spring-data.md`](../spring-boot/spring-data.md) para el mismo problema (N+1, migraciones, entidad de persistencia vs. dominio) del lado de Spring Data JPA, y [`../../ddd/repository-pattern.md`](../../ddd/repository-pattern.md) para el Repository como concepto de DDD, independiente del ORM concreto.
