# Design Patterns in Java

This project is a hands-on exploration of common software design patterns, implemented in Java 21 with Gradle.

## What Are Design Patterns?

In software engineering, a **design pattern** is a general, reusable solution to a commonly occurring problem within a given context in software design. It is not a finished design that can be transformed directly into source or machine code. Rather, it is a description or template for how to solve a problem that can be used in many different situations.

Design patterns are formalized best practices that a programmer can use to solve common problems when designing an application or system. They can speed up the development process by providing tested, proven development paradigms.

Reusing design patterns helps to prevent subtle issues that can cause major problems and improves code readability for coders and architects who are familiar with the patterns.

This repository will cover various patterns, providing clear examples and explanations for each one.

## Design Pattern Categories

This repository is organized into the main categories of design patterns. Each category will have its own detailed `README` file with explanations and diagrams for the patterns it contains.

### Creational Patterns

These patterns provide various object creation mechanisms, which increase flexibility and reuse of existing code. They help make a system independent of how its objects are created, composed, and represented.

For detailed information on the creational patterns implemented in this project, such as **Factory Method** and **Abstract Factory**, please see the [Creational Patterns README](./src/main/java/ec/com/pattern/creational/abstractfactory/README-CREATIONAL.md).

### Structural Patterns

These patterns explain how to assemble objects and classes into larger structures, while keeping these structures flexible and efficient.

*(More information will be added here)*

### Behavioral Patterns

These patterns are concerned with algorithms and the assignment of responsibilities between objects.

*(More information will be added here)*

### Herencia (Inheritance)
- **Relación:** Una clase (subclase) hereda propiedades y comportamientos de otra (superclase). Representa una relación "es un".
- **Notación UML:** Línea sólida con una flecha de triángulo hueco que apunta a la superclase.
- **Ejemplo:**
  ```mermaid
  classDiagram
    Animal <|-- Perro
  ```

### Asociación (Association)
- **Relación:** Describe una conexión entre clases. Es la relación más general.
- **Notación UML:** Línea sólida entre clases.
- **Ejemplo:**
  ```mermaid
  classDiagram
    Persona -- Direccion
  ```

### Agregación (Aggregation)
- **Relación:** Una forma especializada de asociación que representa una relación "tiene un" donde las clases tienen ciclos de vida independientes.
- **Notación UML:** Línea sólida con un rombo hueco en el lado del contenedor.
- **Ejemplo:**
  ```mermaid
  classDiagram
    Universidad o-- Facultad
  ```

### Composición (Composition)
- **Relación:** Una forma fuerte de agregación donde el ciclo de vida de la clase contenida depende de la clase contenedora. Representa una relación "es parte de".
- **Notación UML:** Línea sólida con un rombo relleno en el lado del contenedor.
- **Ejemplo:**
  ```mermaid
  classDiagram
    Casa *-- Habitacion
  ```

### Dependencia (Dependency)
- **Relación:** Ocurre cuando un cambio en una clase puede afectar a otra clase, sin que haya una relación estructural directa.
- **Notación UML:** Línea discontinua con una flecha abierta.
- **Ejemplo:**
  ```mermaid
  classDiagram
    Controlador ..> Servicio
  ```

## Summary of UML Line Notations

Here is a quick visual summary of the lines used for different relationships in UML Class Diagrams:

| Relationship      | Line Style                  | Description                                      |
| ----------------- | --------------------------- | ------------------------------------------------ |
| **Inheritance**   | `-----------|>`             | Solid line with a hollow triangle arrow          |
| **Association**   | `-----------------`         | Solid line                                       |
| **Aggregation**   | `<>----------------`        | Solid line with a hollow diamond                 |
| **Composition**   | `<+>---------------`        | Solid line with a filled diamond                 |
| **Dependency**    | `.. .. .. .. .. >`          | Dashed line with an open arrow                   |


![UML Class Diagram Relationships](https://blog.visual-paradigm.com/wp-content/uploads/2022/02/uml-class-diagram-relationships.png)