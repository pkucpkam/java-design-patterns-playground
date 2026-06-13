## Project Vision

Create a comprehensive Java Design Patterns repository that demonstrates both theoretical understanding and practical implementation.

The repository must emphasize:

* Clean Code
* SOLID Principles
* Maintainability
* Testability
* Enterprise-level thinking

---

# Pattern Implementation Standard

Every pattern MUST contain:

## 1. Problem Description

Explain:

* What problem exists
* Why traditional implementation fails
* Which SOLID principle is violated

---

## 2. Before Refactoring

Provide a bad implementation.

Example:

```java
if (paymentType.equals("CARD")) {
    ...
}
else if (paymentType.equals("PAYPAL")) {
    ...
}
```

---

## 3. Pattern Solution

Provide the pattern implementation.

Example:

```java
public interface PaymentStrategy
```

---

## 4. UML Diagram

Required.

Can be:

* PlantUML
* Mermaid
* Draw.io

---

## 5. Unit Tests

Minimum requirements:

* Happy Path
* Edge Cases
* Failure Cases

Coverage target:

80%+

---

## 6. Real-world Example

Required.

Examples:

| Pattern                 | Business Use Case    |
| ----------------------- | -------------------- |
| Strategy                | Payment Methods      |
| Observer                | Order Tracking       |
| Builder                 | User Registration    |
| Adapter                 | External APIs        |
| State                   | Order Lifecycle      |
| Chain of Responsibility | Validation Pipeline  |
| Decorator               | Discount System      |
| Factory                 | Notification Service |

---

## 7. Spring Boot Version

Required after Pure Java version is completed.

Must demonstrate:

* Dependency Injection
* Bean Management
* Configuration
* Production-oriented usage

---

# Coding Standards

## Java Version

Java 21

---

## Naming Convention

Pattern classes should clearly reflect the role.

Example:

```java
PaymentStrategy
PaypalPaymentStrategy
CreditCardPaymentStrategy
```

Avoid:

```java
StrategyA
StrategyB
```

---

## Package Structure

```text
behavioral
└── strategy
    ├── before
    ├── after
    ├── tests
    └── docs
```

---

# Documentation Standard

Each pattern README must contain:

* Overview
* Problem
* Solution
* UML
* Advantages
* Disadvantages
* Use Cases
* Related Patterns
* References

---

# Definition of Done

A pattern is considered completed only when:

* Code compiles
* Unit tests pass
* README exists
* UML exists
* Business use case exists
* Spring Boot example exists
* Review checklist completed

All conditions must be satisfied.
