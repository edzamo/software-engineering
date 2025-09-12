¡Hola, vamos a sumergirnos en el fascinante mundo de los patrones de diseño. Empezaremos con uno de los más útiles y sencillos para iniciarse: el **Factory Method** (Método de Fábrica).

A lo largo de este post, veremos la teoría, un ejemplo práctico y, lo mejor de todo, lo "aterrizaremos" con un caso de uso 100% ecuatoriano.

## ¿Qué es y cómo funciona el Factory Method?

El **Factory Method** es un patrón de diseño creacional que resuelve el problema de crear objetos sin especificar sus clases concretas. ¿Suena complicado? Para nada.

Imagina que tienes una clase que necesita crear objetos, pero no sabe exactamente *cuáles* creará. Dependerá de una configuración, de parámetros o del entorno. En lugar de llenar tu código con `if-else` o `switch` para instanciar cada objeto con `new`, delegas esa responsabilidad a un método especial: el **método de fábrica**.

Esto nos da una flexibilidad increíble, ya que el código principal trabaja con una interfaz común y no le importa la implementación específica del objeto que recibe.

## ¿Cómo lo podría usar? El Ejemplo Clásico: Un Sistema de Pagos

En nuestro proyecto, tenemos un ejemplo perfecto que simula un sistema de pagos.

1.  **El Producto (`Payment`):** Primero, definimos una interfaz que establece el contrato. Todos los métodos de pago deben tener una forma de "hacer el pago".

    ```java
    // src/main/java/ec/com/pattern/creational/factorymethod/payment/Payment.java
    public interface Payment {
        void doPayment();
    }
    ```

2.  **Los Productos Concretos (`CardPayment`, `GooglePayment`):** Luego, creamos las clases que implementan esa interfaz. Cada una representa un tipo de pago específico.

    ```java
    // src/main/java/ec/com/pattern/creational/factorymethod/payment/CardPayment.java
    public class CardPayment implements Payment {
        public void doPayment() {
            System.out.println("Making payment with Card");
        }
    }
    ```

3.  **La Fábrica (`PaymentFactory`):** Aquí está la magia. Creamos una clase cuyo único trabajo es crear y devolver el objeto correcto según un parámetro.

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

### 🔹 Explicación simple con un ejemplo del mundo real

Imagina que trabajas en un banco:
1.  El cliente elige pagar con tarjeta, Google Pay o PayPal.
2.  La fábrica (`PaymentFactory`) recibe esa información (el `TypePayment` enum).
3.  La fábrica crea el objeto apropiado:
    *   Si es `CARD` → devuelve un `CardPayment`.
    *   Si es `GOOGLE` → devuelve un `GooglePayment`.
    *   Si es `PAYPAL` → devuelve un `PaypalPayment`.
4.  El sistema llama a `doPayment()` sin importar el tipo de pago, porque todas las clases implementan la misma interfaz `Payment`.

---

### ¿Cómo se usa la fábrica?

El código que necesita un objeto de pago (el "cliente") ya no tiene que saber cómo crear un `CardPayment` o un `GooglePayment`. Simplemente le pide a la fábrica el tipo que necesita y trabaja con la interfaz `Payment`.

```java
public class Main {
    public static void main(String[] args) {
        // El cliente solo pide el tipo de pago que quiere
        Payment payment = PaymentFactory.buildPayment(TypePayment.CARD);
        payment.doPayment(); // Salida: Making payment with Card
    }
}
```
### 👉 En resumen:

Este diagrama muestra un patrón Factory donde una fábrica centraliza la creación de diferentes tipos de pago, usando un `enum` para decidir qué clase concreta instanciar.

![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/5i9pna3t6pcmnu8mzej4.png)

## Adaptándolo "a lo Criollo": Una Picantería en Manabí

Ahora, ¡vamos a lo nuestro! Olvidémonos de los pagos y la tecnología por un momento y pensemos en algo que nos identifica: la comida.

Imagina que estamos montando el software para una picantería en Portoviejo. En Manabí, un mismo plato puede tener variaciones. Por ejemplo, un "ceviche" puede ser de pescado, de camarón, o mixto. Todos son ceviches, pero se preparan de forma ligeramente distinta.

Aquí es donde el Factory Method brilla.

---

### 💻 Código Fuente del Ejemplo

Puedes encontrar todo el código de este ejemplo en la carpeta del proyecto en GitHub:
*   **Ver el código del ejemplo del Ceviche Manabita**

## Estructura de archivos (ejemplo)
```
src/
 ├─ Ceviche.java                # interfaz producto
 ├─ CevicheType.java            # enum con tipos
 ├─ FishCeviche.java
 ├─ ShrimpCeviche.java
 ├─ MixedCeviche.java
 ├─ JipijapaCeviche.java
 └─ ManabitaRestaurantFactory.java
```


![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/zdnotww7icwoit72376r.png)


