# Design Patterns in Java

This project provides examples of various design patterns implemented in Java. The patterns are categorized into Creational, Structural, and Behavioral patterns.

## A Quick Introduction to UML

UML (Unified Modeling Language) is a standardized modeling language used to visualize, specify, construct, and document the artifacts of a software system. It is a graphical language that helps in describing and designing software systems.

### Key Concepts of UML:

*   **Diagrams:** UML includes various diagrams to represent different aspects of a system. The most common ones are:
    *   **Class Diagram:** Describes the structure of a system by showing the system's classes, their attributes, operations (or methods), and the relationships among objects.
    *   **Use Case Diagram:** Shows how users interact with the system.
    *   **Sequence Diagram:** Shows how objects communicate with each other in terms of a time sequence.
    *   **Activity Diagram:** Describes the flow of control in a system.
*   **Relationships:** UML defines several types of relationships between classes:
    *   **Association:** A structural relationship that describes a set of links, which are connections among objects.
    *   **Aggregation:** A special type of association that represents a "part-of" or "has-a" relationship.
    *   **Composition:** A strong type of aggregation where the "part" object cannot exist without the "whole" object.
    *   **Inheritance (or Generalization):** A relationship between a general class (the superclass or parent) and a more specific class (the subclass or child).
    *   **Dependency:** A relationship in which one element, the client, depends on another element, the supplier.

### UML Relationship Diagrams

Here are some examples of UML relationship diagrams using ASCII art:

**Association:**
```
+-----------+     +-----------+
|  ClassA   |-----|  ClassB   |
+-----------+     +-----------+
```

**Aggregation:**
```
+-----------+<>---+-----------+
|  ClassA   |     |  ClassB   |
+-----------+     +-----------+
```

**Composition:**
```
+-----------+<*>--+-----------+
|  ClassA   |     |  ClassB   |
+-----------+     +-----------+
```

**Inheritance:**
```
+-----------+     +-----------+
|  Superclass   |-----|  Subclass   |
+-----------+     +-----------+
      ^
      |
+-----------+
|  Subclass   |
+-----------+
```

**Dependency:**
```
+-----------+     +-----------+
|  Client   |----->|  Service  |
+-----------+     +-----------+
```

### UML Class Diagram Example

Here is an example of a class diagram:
```
+-----------------+
|      Animal     |
+-----------------+
| -name: String   |
+-----------------+
| +getName(): String |
| +setName(name: String): void |
| +makeSound(): void |
+-----------------+
        ^
        |
+-----------------+
|       Dog       |
+-----------------+
| -breed: String  |
+-----------------+
| +getBreed(): String |
| +setBreed(breed: String): void |
| +makeSound(): void |
| +wagTail(): void |
+-----------------+
```

## Creational Patterns

Creational design patterns are concerned with the process of object creation. They provide ways to create objects while hiding the creation logic, rather than instantiating objects directly using the `new` operator. This gives the program more flexibility in deciding which objects need to be created for a given use case.

### Abstract Factory

**Intent:** Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

**Diagram:**
```
[Client] -> [AbstractFactory] <- [ConcreteFactory1]
  ^                  ^                ^
  |                  |                |
  |                  v                v
  +-----> [AbstractProductA] <- [ProductA1]
  |                  ^                ^
  |                  |                |
  +-----> [AbstractProductB] <- [ProductB1]
```

**Implementation:** The `creational/abstractfactory` package contains an example of the Abstract Factory pattern. The `AppCarFactory` acts as the client, and the `CarAbstractFactory` is the abstract factory. The `HatchbackFactory` and `SedanCarFactory` are concrete factories that create different types of cars.

### Builder

**Intent:** Separate the construction of a complex object from its representation so that the same construction process can create different representations.

**Diagram:**
```
[Director] -> [Builder] <- [ConcreteBuilder]
                 ^                ^
                 |                |
                 +-----> [Product] <+---- 
```

**Implementation:** The `creational/builder` package contains several examples of the Builder pattern. The `guru` example shows how to build different types of cars using a `DirectorDealership` and a `CarBuilder`.

### Factory Method

**Intent:** Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

**Diagram:**
```
[Creator] ------------> [Product]
   ^                      ^
   |                      |
[ConcreteCreator] --> [ConcreteProduct]
```

**Implementation:** The `creational/factory` package contains an example of the Factory Method pattern. The `CarFactory` is the creator, and the `MastodonFactory` and `RhinoFactory` are concrete creators that create different types of cars.

### Prototype

**Intent:** Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

**Diagram:**
```
[Client] -> [Prototype] <--- [ConcretePrototype1]
             (clone)           (clone)
```

**Implementation:** The `creational/prototype` package contains an example of the Prototype pattern.

### Singleton

**Intent:** Ensure a class only has one instance, and provide a global point of access to it.

**Diagram:**
```
[Client] -> [Singleton]
             -instance
             +getInstance()
```

**Implementation:** The `creational/singleton` package contains several examples of the Singleton pattern. The `DbSingleton` class is a classic example of a Singleton that manages a database connection.

## Structural Patterns

Structural design patterns are concerned with how classes and objects are composed to form larger structures. They simplify the structure by identifying relationships.

### Adapter

**Intent:** Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

**Diagram:**
```
[Client] -> [Target] <|-- [Adapter] -> [Adaptee]
```

**Implementation:** The `structural/adapter` package contains an example of the Adapter pattern.

### Bridge

**Intent:** Decouple an abstraction from its implementation so that the two can vary independently.

**Diagram:**
```
[Abstraction] <>-- [Implementor]
   ^                   ^
   |                   |
[RefinedAbstraction]  [ConcreteImplementor]
```

**Implementation:** The `structural/bridge` package contains an example of the Bridge pattern.

### Composite

**Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

**Diagram:**
```
  [Component]
  /    |    \
[Leaf] [Leaf] [Composite]
             /    |    \
           [Leaf] [Leaf] ...
```

**Implementation:** The `structural/composite` package contains an example of the Composite pattern.

### Decorator

**Intent:** Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

**Diagram:**
```
[Component] <|-- [ConcreteComponent]
   ^
   |
[Decorator] <|-- [ConcreteDecoratorA]
   ^
   |
   +------> [Component]
```

**Implementation:** The `structural/decorator` package contains an example of the Decorator pattern.

### Facade

**Intent:** Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

**Diagram:**
```
[Client] -> [Facade] -> [Subsystem Class 1]
                      -> [Subsystem Class 2]
                      -> [Subsystem Class 3]
```

**Implementation:** The `structural/facade` package contains an example of the Facade pattern.

### Flyweight

**Intent:** Use sharing to support large numbers of fine-grained objects efficiently.

**Diagram:**
```
[Client] -> [FlyweightFactory] -> [Flyweight]
```

**Implementation:** The `structural/flyweight` package contains an example of the Flyweight pattern.

### Proxy

**Intent:** Provide a surrogate or placeholder for another object to control access to it.

**Diagram:**
```
[Client] -> [Proxy] -> [RealSubject]
```

**Implementation:** The `structural/proxy` package contains an example of the Proxy pattern.

## Behavioral Patterns

Behavioral design patterns are concerned with algorithms and the assignment of responsibilities between objects.

### Strategy

**Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

**Diagram:**
```
[Context] <>-- [Strategy]
   ^
   |
   +------> [ConcreteStrategyA]
   +------> [ConcreteStrategyB]
```

**Implementation:** The `behavioral/strategy` package contains an example of the Strategy pattern. The `example` subdirectory shows how to use different payment strategies (`PayByCreditCard`, `PayByPayPal`) in an e-commerce application.

### Visitor

**Intent:** Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.

**Diagram:**
```
[Client] -> [ObjectStructure] <>-- [Element] <|-- [ConcreteElementA]
                                     ^
                                     |
                                  [Visitor] <|-- [ConcreteVisitor]
```

**Implementation:** The `behavioral/visitor` package contains an example of the Visitor pattern. The `XMLExportVisitor` is a visitor that exports the shapes (Dot, Circle, Rectangle) to XML.