Hello, let's dive into the fascinating world of design patterns. We'll start with one of the most useful and simple ones to get started: the **Factory Method**.

Throughout this post, we'll look at the theory, a practical example, and best of all, we'll "ground" it with a 100% Ecuadorian use case.

## What is the Factory Method and How Does It Work?

The **Factory Method** is a creational design pattern that solves the problem of creating objects without specifying their concrete classes. Sound complicated? Not at all.

Imagine you have a class that needs to create objects, but it doesn't know exactly *which* ones it will create. It might depend on a configuration, parameters, or the environment. Instead of filling your code with `if-else` or `switch` statements to instantiate each object with `new`, you delegate that responsibility to a special method: the **factory method**.

This gives us incredible flexibility, as the main code works with a common interface and doesn't care about the specific implementation of the object it receives.

## How Could I Use It? The Classic Example: A Payment System

In our project, we have a perfect example that simulates a payment system.

1.  **The Product (`Payment`):** First, we define an interface that establishes the contract. All payment methods must have a way to "make the payment".

    ```java
    // src/main/java/ec/com/pattern/creational/factorymethod/payment/Payment.java
    public interface Payment {
        void doPayment();
    }
    ```

2.  **The Concrete Products (`CardPayment`, `GooglePayment`):** Next, we create the classes that implement that interface. Each one represents a specific type of payment.

    ```java
    // src/main/java/ec/com/pattern/creational/factorymethod/payment/CardPayment.java
    public class CardPayment implements Payment {
        public void doPayment() {
            System.out.println("Making payment with Card");
        }
    }
    ```

3.  **The Factory (`PaymentFactory`):** Here's the magic. We create a class whose sole job is to create and return the correct object based on a parameter.

    ```java
    // src/main/java/ec/com/pattern/creational/factorymethod/payment/PaymentFactory.java
    public class PaymentFactory {
        public static Payment buildPayment(TypePayment typePayment) {
            switch (typePayment) {
                case CARD:
                    return new CardPayment();
                case GOOGLE:
                    return new GooglePayment();
                case PAYPAL:
                    return new PaypalPayment();
                default:
                    throw new IllegalArgumentException("No such payment");
            }
        }
    }
    ```

### 🔹 Simple Explanation with a Real-World Example

Imagine you work at a bank:
1.  The customer chooses to pay with a card, Google Pay, or PayPal.
2.  The factory (`PaymentFactory`) receives that information (the `TypePayment` enum).
3.  The factory creates the appropriate object:
    *   If it's `CARD` → returns a `CardPayment`.
    *   If it's `GOOGLE` → returns a `GooglePayment`.
    *   If it's `PAYPAL` → returns a `PaypalPayment`.
4.  The system calls `doPayment()` regardless of the payment type, because all classes implement the same `Payment` interface.

---

### 👉 In summary:

This diagram shows a Factory pattern where a factory centralizes the creation of different payment types, using an `enum` to decide which concrete class to instantiate.

!Image description

## Adapting It "to the Local Style": A Seafood Restaurant in Manabí

Now, let's get to our own thing! Let's forget about payments and technology for a moment and think about something that defines us: food.

Imagine we're building the software for a seafood restaurant in Portoviejo. In Manabí, the same dish can have variations. For example, a "ceviche" can be made with fish, shrimp, or a mix. They are all ceviches, but they are prepared slightly differently.

This is where the Factory Method shines.

---

## File Structure (example)
```
src/
 ├─ Ceviche.java                # product interface
 ├─ CevicheType.java            # enum with types
 ├─ FishCeviche.java
 ├─ ShrimpCeviche.java
 ├─ MixedCeviche.java
 ├─ JipijapaCeviche.java
 └─ ManabitaRestaurantFactory.java
```


!Image description


https://dev.to/edzamo/demystifying-the-factory-method-pattern-with-a-manabita-twist-ne6