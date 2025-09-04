# SOLID Principles

This repository contains code examples that illustrate the five SOLID principles of object-oriented design.

## What are the SOLID principles?

SOLID is a mnemonic acronym for five design principles intended to make software designs more understandable, flexible, and maintainable.

### 1. Single Responsibility Principle (SRP)

> "A class should have one and only one reason to change."

This means that a class should have only one responsibility or job. If a class has more than one responsibility, these responsibilities become coupled. Changes in one responsibility can lead to changes in the other.

┌─────────────────────────┐           ┌──────────────────┐ ┌─────────────────┐ ┌────────────────┐
│     PasswordManager     │           │  PasswordHasher  │ │ PasswordStorage │ │    Notifier    │
├─────────────────────────┤           ├──────────────────┤ ├─────────────────┤ ├────────────────┤
│ + hashPassword()        │           │ + hash()         │ │ + save()        │ │ + notify()     │
│ + savePasswordToDb()    │   ====>   └──────────────────┘ └─────────────────┘ └────────────────┘
│ + notifyUser()          │
 responsabilidades)                   responsabilidad única)
```

### 2. Open/Closed Principle (OCP)

> "Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification."

```text
          VIOLACIÓN DE OCP                               APLICANDO OCP
    ┌───────────────────────────┐
    │      Calculator           │
    ├───────────────────────────┤                      ┌──────────────────┐
    │     else if (op is Sub)   │                      ├──────────────────┤
    │     // ¡MODIFICAR AQUÍ!   │                      │ + perform()      │
    │   }                       │                      └──────────────────┘
    └───────────────────────────┘                              ▲
    (Cerrado para extensión,                                   | (Implementa)
     abierto para modificación)                     ┌──────────┴──────────┐
                                          │    Addition     │   │  Subtraction  │
                                          └─────────────────┘   └─────────────────┘
                                                               (Abierto a extensión
                                                                sin modificar código)
```

### 3. Liskov Substitution Principle (LSP)

> "Subtypes must be substitutable for their base types."

This means that child classes should be usable in place of their parent classes without causing errors. If code using a reference to the base class cannot also use a reference to a derived class of that base class, then the derived class violates the LSP.

```text
         VIOLACIÓN DE LSP                               APLICANDO LSP
      ┌──────────┐                                  ┌──────────┐       ┌────────────────┐
      │   Bird   │                                  │   Bird   │       │ <<interface>>  │
      ├──────────┤                                  └──────────┘       │    Flying      │
      │ + fly()  │                                       ▲             ├────────────────┤
      └──────────┘                                       │             │ + fly()        │
           ▲                                             │ (Hereda)    └────────────────┘
           │ (Hereda)                                    │                      ▲
    ┌──────┴──────┐                                      │                      │ (Implementa)
    │             │                              ┌───────┴──────┐         ┌─────┴─────┐
┌───┴───┐     ┌───┴────┐                         │    Penguin   │         │    Duck   │
│ Duck  │     │Penguin │                         └──────────────┘         └───────────┘
└───────┘     └────────┘
              (Lanza Excepción
               en fly())
```

### 4. Interface Segregation Principle (ISP)

> "No client should be forced to depend on interfaces it does not use."

This principle suggests that it is better to have many small, client-specific interfaces than one large, general-purpose interface.

```text
      VIOLACIÓN DE ISP ("Interfaz Gorda")           APLICANDO ISP (Interfaces Segregadas)

   ┌───────────────────────────┐
   │      <<interface>>        │                 ┌───────────┐  ┌───────────┐
   │      IWorker             │                 │ <<iface>> │  │ <<iface>> │
   ├───────────────────────────┤                 │  IWork    │  │  IEat     │
   │ + work()                  │                 └───────────┘  └───────────┘
   │ + eat()                   │                         ▲            ▲
   └───────────────────────────┘                         │            │
                 ▲                                       └─────┬──────┘
                 │ (Implementa)                                │
       ┌─────────┴─────────┐                           ┌───────┴───────┐
       │       Robot       │                           │     Human     │ (Implementa IWork, IEat)
       ├───────────────────┤                           └───────────────┘
       │ + work()          │                           ┌───────┴───────┐
       │ + eat() // ¡ERROR!│                           │      Robot    │ (Implementa solo IWork)
       └───────────────────┘                           └───────────────┘
       (Forzado a implementar
        métodos que no usa)
```

### 5. Dependency Inversion Principle (DIP)

> "High-level modules should not depend on low-level modules. Both should depend on abstractions."
> "Abstractions should not depend on details. Details should depend on abstractions."

This means we should depend on abstractions (interfaces and abstract classes) instead of concrete implementations.

```text
            VIOLACIÓN DE DIP                               APLICANDO DIP

┌──────────────────┐                             ┌──────────────────┐
│  PasswordService │                             │  PasswordService │
└──────────────────┘                             └──────────────────┘
         │                                                  │
         │ Depende directamente de...                       │ Depende de la abstracción...
         ▼                                                  ▼
┌──────────────────┐                             ┌──────────────────┐
│  MySQLDatabase   │                             │  <<interface>>   │
│ (Implementación) │                             │ Database         │
└──────────────────┘                             └──────────────────┘
                                                            ▲
                                                            │ (Implementa)
                                                 ┌──────────┴──────────┐
                                                 │                     │
                                       ┌─────────┴───────┐   ┌─────────┴───────┐
                                       │  MySQLDatabase  │   │  OracleDatabase │
                                       └─────────────────┘   └─────────────────┘
```
