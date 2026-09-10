## Structural Design Patterns

Structural patterns explain how to assemble objects and classes into larger structures, keeping those structures flexible and efficient. Unlike creational patterns (how objects are built) or behavioral patterns (how objects communicate), structural patterns are about **composition**: wrapping, adapting, or combining objects without coupling their concrete implementations.

This document covers all 7 GoF structural patterns as short pills — one paragraph of theory, a minimal example, and a "when to use it" note.

1.  **Adapter**
2.  **Bridge**
3.  **Composite**
4.  **Decorator**
5.  **Facade**
6.  **Flyweight**
7.  **Proxy**

---

### 1. Adapter

Converts the interface of one class into another interface the client expects, so two incompatible interfaces can work together without changing either one's source code.

```java
// Existing (incompatible) class you can't modify
class LegacyPaymentGateway {
    void makePayment(String amountInCents) { /* ... */ }
}

// Target interface your app already uses
interface Payment { void doPayment(double amount); }

// Adapter bridges the gap
class LegacyPaymentAdapter implements Payment {
    private final LegacyPaymentGateway legacy;
    LegacyPaymentAdapter(LegacyPaymentGateway legacy) { this.legacy = legacy; }

    @Override
    public void doPayment(double amount) {
        legacy.makePayment(String.valueOf((int) (amount * 100)));
    }
}
```

🔹 **When to use it:** integrating a third-party library or legacy code whose interface doesn't match what the rest of your app expects, and you can't (or shouldn't) modify it directly. Classic case: wrapping an old SOAP client behind the same port interface your hexagonal architecture already defines.

---

### 2. Bridge

Splits a large class (or a set of closely related classes) into two separate hierarchies — **abstraction** and **implementation** — that can be developed and extended independently, connected by composition instead of inheritance.

```java
// Implementation hierarchy
interface Renderer { void renderShape(String shape); }
class VectorRenderer implements Renderer { public void renderShape(String s) { /* draw as vectors */ } }
class RasterRenderer implements Renderer { public void renderShape(String s) { /* draw as pixels */ } }

// Abstraction hierarchy — delegates to Renderer instead of extending it
abstract class Shape {
    protected Renderer renderer;
    Shape(Renderer renderer) { this.renderer = renderer; }
    abstract void draw();
}
class Circle extends Shape {
    Circle(Renderer renderer) { super(renderer); }
    void draw() { renderer.renderShape("circle"); }
}
```

🔹 **When to use it:** you have two dimensions of variation that would otherwise explode into a class-per-combination (`VectorCircle`, `RasterCircle`, `VectorSquare`, `RasterSquare`...). Bridge lets `Shape` and `Renderer` vary independently — add a new shape or a new renderer without touching the other hierarchy.

---

### 3. Composite

Lets you compose objects into tree structures and treat individual objects and compositions of objects uniformly — the client doesn't need to know whether it's talking to a leaf or a branch.

```java
interface FileSystemNode { long size(); }

class File implements FileSystemNode {
    private final long bytes;
    File(long bytes) { this.bytes = bytes; }
    public long size() { return bytes; }
}

class Folder implements FileSystemNode {
    private final List<FileSystemNode> children = new ArrayList<>();
    void add(FileSystemNode node) { children.add(node); }
    public long size() { return children.stream().mapToLong(FileSystemNode::size).sum(); }
}
```

🔹 **When to use it:** representing part-whole hierarchies — file systems, UI component trees, org charts, nested menus. The moment you catch yourself writing `if (node instanceof Folder) { ...recurse... } else { ...leaf logic... }` all over the client code, Composite removes that branching.

---

### 4. Decorator

Attaches new behavior to an individual object dynamically by wrapping it in a decorator object with the same interface, without altering the original class or affecting other instances.

```java
interface Coffee { double cost(); }

class SimpleCoffee implements Coffee { public double cost() { return 2.0; } }

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee wrapped;
    CoffeeDecorator(Coffee wrapped) { this.wrapped = wrapped; }
}

class WithMilk extends CoffeeDecorator {
    WithMilk(Coffee c) { super(c); }
    public double cost() { return wrapped.cost() + 0.5; }
}

class WithCaramel extends CoffeeDecorator {
    WithCaramel(Coffee c) { super(c); }
    public double cost() { return wrapped.cost() + 0.7; }
}

// Coffee order = new WithCaramel(new WithMilk(new SimpleCoffee()));
```

🔹 **When to use it:** adding optional, combinable responsibilities to an object at runtime, as an alternative to subclassing every combination (`CoffeeWithMilk`, `CoffeeWithMilkAndCaramel`, ...). Common in Java I/O (`BufferedReader(new FileReader(...))`) and in web frameworks wrapping `HttpServletRequest`.

---

### 5. Facade

Provides a simplified, unified interface to a complex subsystem of classes, hiding the internal complexity from the client.

```java
class InventoryService { boolean reserve(String sku) { return true; } }
class PaymentService { boolean charge(String cardToken, double amount) { return true; } }
class ShippingService { void schedule(String orderId) { /* ... */ } }

class OrderFacade {
    private final InventoryService inventory = new InventoryService();
    private final PaymentService payment = new PaymentService();
    private final ShippingService shipping = new ShippingService();

    void placeOrder(String sku, String cardToken, double amount, String orderId) {
        if (inventory.reserve(sku) && payment.charge(cardToken, amount)) {
            shipping.schedule(orderId);
        }
    }
}
```

🔹 **When to use it:** a client (or another microservice/controller) needs to orchestrate several subsystems to accomplish one use case. In hexagonal architecture, a use case implementation is essentially a Facade over multiple output ports.

---

### 6. Flyweight

Minimizes memory usage by sharing as much data as possible with other similar objects, separating **intrinsic state** (shared, immutable) from **extrinsic state** (unique, passed in by the client).

```java
class CharacterGlyph { // intrinsic state: the font glyph itself, expensive to create
    private final char symbol;
    private final String font;
    CharacterGlyph(char symbol, String font) { this.symbol = symbol; this.font = font; }
}

class GlyphFactory {
    private static final Map<String, CharacterGlyph> cache = new HashMap<>();
    static CharacterGlyph get(char symbol, String font) {
        return cache.computeIfAbsent(symbol + font, k -> new CharacterGlyph(symbol, font));
    }
}
// Extrinsic state (x, y position on screen) is passed separately at render time
```

🔹 **When to use it:** you need to create a huge number of similar objects (glyphs in a text editor, tree instances in a game map, icon objects) and object count is causing real memory pressure. It's a niche, performance-driven pattern — don't reach for it unless profiling actually shows object churn/memory as the bottleneck.

---

### 7. Proxy

Provides a surrogate/placeholder object that controls access to another object, letting you add behavior (lazy loading, access control, caching, logging) before or after the request reaches the real object — without the client knowing the difference.

```java
interface UserRepository { User findById(String id); }

class RealUserRepository implements UserRepository {
    public User findById(String id) { /* hits the database */ return new User(id); }
}

class CachingUserRepositoryProxy implements UserRepository {
    private final RealUserRepository real = new RealUserRepository();
    private final Map<String, User> cache = new HashMap<>();

    public User findById(String id) {
        return cache.computeIfAbsent(id, real::findById);
    }
}
```

🔹 **When to use it:** lazy initialization (don't build an expensive object until it's actually used), access control (check permissions before delegating), caching, or logging/metrics around calls to a real object — all without touching the real object's code. Spring's `@Transactional`/`@Cacheable` proxies and JPA lazy-loaded entities are Proxy in production.

---

## Quick comparison: Adapter vs Decorator vs Proxy vs Facade

These four are the ones people mix up most in interviews — they all "wrap" another object, but for different reasons:

| Pattern | Same interface as wrapped object? | Purpose |
|---|---|---|
| **Adapter** | No — converts one interface to another | Compatibility between mismatched interfaces |
| **Decorator** | Yes | Add behavior/responsibilities dynamically, combinable |
| **Proxy** | Yes | Control access (lazy load, cache, security, logging) |
| **Facade** | New, simpler interface over several classes | Simplify a complex subsystem |

Related: [`creational-patterns.md`](creational-patterns.md), [`behavioral-patterns.md`](behavioral-patterns.md), [`solid-principles/`](../solid-principles) — Adapter and Facade are concrete applications of the Dependency Inversion and Interface Segregation principles.

## References

- Gamma, E., Helm, R., Johnson, R., Vlissides, J. (Gang of Four) — *Design Patterns: Elements of Reusable Object-Oriented Software* (1994).
- [Refactoring.Guru — Structural Patterns](https://refactoring.guru/design-patterns/structural-patterns).
