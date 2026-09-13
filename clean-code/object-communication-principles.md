# Cómo se hablan los objetos entre sí — Law of Demeter, Tell-Don't-Ask, Hollywood Principle

La pregunta que dispara este documento: *"¿por qué `order.getCustomer().getAddress().getCity()` es una señal de alarma en un code review, si compila y funciona?"* — porque cada `.get...()` encadenado es una nueva dependencia oculta sobre la **estructura interna** de un objeto que no es el tuyo. Estos tres principios son formas distintas de la misma idea: pedile a un objeto que **haga** algo, no le preguntes su estado para decidir vos desde afuera.

## Law of Demeter — "no hables con extraños"

**Regla:** un método solo debería llamar a métodos de: (1) el propio objeto, (2) sus parámetros, (3) objetos que crea él mismo, (4) sus propios atributos directos. Nunca a un objeto que obtuviste **navegando** a través de otro (`a.getB().getC().doSomething()`).

```java
// Viola Law of Demeter — encadena 3 niveles de conocimiento estructural
String city = order.getCustomer().getAddress().getCity();

// Aplica LoD — Order expone lo que hace falta, sin revelar su estructura interna
String city = order.getShippingCity();
```

```java
public class Order {
    private Customer customer;

    public String getShippingCity() {
        return customer.getAddress().getCity(); // Order SÍ puede navegar su propio grafo interno
    }
}
```

- La regla no prohíbe navegar relaciones — prohíbe que el **código que llama desde afuera** lo haga. `Order` puede perfectamente conocer su propia estructura interna (`customer.getAddress()`); lo que no debería pasar es que un `OrderController` o un `ShippingService` externo conozca que `Order` tiene un `Customer` que tiene una `Address` que tiene una `city`.
- **Por qué importa de verdad**: si mañana `Address` cambia de forma (ej. se agrega soporte multi-idioma y `city` pasa a ser un objeto `LocalizedString`), con la cadena de `getters` **todos los llamadores externos** que hicieron `getCustomer().getAddress().getCity()` se rompen. Con `order.getShippingCity()`, el cambio se absorbe en un solo lugar.
- **Frase para entrevista:** "cada `.` extra en una cadena de getters es una dependencia nueva sobre una estructura que no controlás — Law of Demeter minimiza cuántos objetos necesitan saber cómo está armado otro."

## Tell, Don't Ask — decile qué hacer, no le preguntes su estado

**Regla:** en vez de preguntarle a un objeto su estado y decidir con un `if` desde afuera, decile directamente qué hacer y dejá que él decida internamente.

```java
// "Ask" — el llamador pregunta el estado y decide la regla de negocio afuera
if (account.getBalance() >= amount) {
    account.setBalance(account.getBalance() - amount);
} else {
    throw new InsufficientBalanceException();
}

// "Tell" — el objeto decide, protege su propia invariante
account.withdraw(amount); // adentro: valida saldo, lanza excepción o descuenta
```

```java
public class Account {
    private BigDecimal balance;

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(amount);
        }
        balance = balance.subtract(amount);
    }
}
```

- Es la misma idea central que separa un **Rich Domain Model** de un **Anemic Domain Model** en DDD (ver [`ddd/entities-vs-value-objects.md`](../ddd/entities-vs-value-objects.md), sección "Anemic Domain Model vs Rich Domain Model") — "Ask" es exactamente el patrón que produce anemia de dominio: el objeto se convierte en una bolsa de getters/setters y la regla de negocio migra afuera, donde puede duplicarse o ser olvidada por otro llamador.
- El beneficio no es solo estético: con "Tell", la invariante (`balance >= 0`) está garantizada por el **único** punto de entrada (`withdraw`) — es imposible dejar la cuenta en un estado inválido sin pasar por esa validación. Con "Ask", cualquier código nuevo que "se olvide" de repetir el `if` produce el bug.

## Hollywood Principle — "no nos llames, nosotros te llamamos"

**Regla:** en vez de que tu código llame activamente a un framework/librería para preguntar "¿ya puedo continuar?" o "¿qué necesitás?", el framework te llama a vos, en el momento que él decide — es el **Inversion of Control** que ya vimos en [`frameworks/spring-boot/fundamentals.md`](../frameworks/spring-boot/fundamentals.md) y [`frameworks/nestjs/fundamentals.md`](../frameworks/nestjs/fundamentals.md), pero como principio de diseño general, no solo como mecanismo de un framework concreto.

```java
// Sin Hollywood Principle — tu código pregunta activamente, controla el flujo
while (!connection.isReady()) {
    Thread.sleep(100); // polling manual — vos llamás al framework para preguntar
}
connection.send(data);

// Con Hollywood Principle — te registrás, el framework te llama cuando corresponde
connection.onReady(() -> connection.send(data)); // callback — el framework decide cuándo
```

- Es el principio que explica **por qué** un `@Controller`/`@RestController` nunca instancia el framework que lo invoca — es al revés: el framework escanea, encuentra tu clase anotada, y **te llama** cuando llega un request. Vos nunca hacés `new Controller().handleRequest()`.
- Se relaciona directamente con el **Dependency Inversion Principle** (la "D" de SOLID, ver [`solid-principles/`](../solid-principles)) — DIP es la versión aplicada a nivel de dependencias entre clases (dependé de abstracciones); Hollywood Principle es la versión aplicada al **flujo de control** (no llames vos, dejá que te llamen).

## Por qué se agrupan los tres

Los tres principios atacan el mismo riesgo desde ángulos distintos: **acoplamiento por conocimiento excesivo de un objeto sobre otro.**

| Principio | Qué evita concretamente |
|---|---|
| Law of Demeter | Que un llamador conozca la **estructura interna** (el grafo de objetos) de otro objeto. |
| Tell, Don't Ask | Que un llamador conozca el **estado interno** de un objeto para decidir una regla que debería vivir adentro. |
| Hollywood Principle | Que tu código dependa de **cuándo** un framework/componente externo decide actuar, en vez de que el control fluya hacia vos cuando corresponde. |

## Drills de repaso

| Tiempo | Pregunta |
|---|---|
| 4 min | "Encontrás `a.getB().getC().getD()` en un code review. ¿Qué principio se viola y cómo lo arreglarías?" |
| 5 min | "Refactorizá un `if (cuenta.getSaldo() >= monto) { ... } else { throw ... }` externo a Tell-Don't-Ask." |
| 4 min | "¿Cómo se relaciona el Hollywood Principle con Dependency Inversion? ¿Son lo mismo?" |
| 4 min | "¿Por qué 'Ask' tiende a producir un Anemic Domain Model con el tiempo?" |

## Referencias

- Lieberherr, K. — origen de Law of Demeter (1987, Northeastern University).
- Fowler, M. — [*TellDontAsk*](https://martinfowler.com/bliki/TellDontAsk.html) — la formulación más citada del principio.
- Martin, R. C. — *Clean Code* (2008) y *Agile Software Development, Principles, Patterns, and Practices* (2002) — Law of Demeter y su relación con acoplamiento.

Relacionado: [`ddd/entities-vs-value-objects.md`](../ddd/entities-vs-value-objects.md) para el Anemic Domain Model como consecuencia práctica de violar Tell-Don't-Ask, [`solid-principles/`](../solid-principles) para Dependency Inversion (la cara "dependencias" del Hollywood Principle), y [`code-review.md`](code-review.md) para dónde entran estos tres principios dentro del checklist general de revisión.
