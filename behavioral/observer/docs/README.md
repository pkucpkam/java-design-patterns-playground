# Observer Pattern

## Overview
The Observer Pattern is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they're observing.

## 1. Problem Description
**What problem exists:** When a subject (like an Order) changes its state, multiple dependent objects (like EmailTracker, SMSTracker) need to be notified and updated automatically.

**Why traditional implementation fails:** In a traditional approach, the `OrderService` creates instances of all tracking services directly and calls their methods. This leads to tight coupling. If we want to add a new notification type, we have to modify the existing `OrderService` code.

**Which SOLID principle is violated:** 
- **Open/Closed Principle (OCP):** Adding a new notification method requires modifying the `OrderService` class.
- **Dependency Inversion Principle (DIP):** `OrderService` depends on concrete classes (like `EmailTracker`) rather than abstractions.

---

## 2. Before Refactoring
```java
public class OrderService {
    private EmailTracker emailTracker = new EmailTracker();
    private SmsTracker smsTracker = new SmsTracker();

    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
        
        // Tightly coupled
        emailTracker.sendEmail(order.getCustomerEmail(), "Status: " + newStatus);
        smsTracker.sendSms(order.getCustomerPhone(), "Status: " + newStatus);
    }
}
```

---

## 3. Pattern Solution
Create an `OrderObserver` interface with an `update()` method and an `OrderSubject` interface to manage subscriptions. Concrete trackers implement `OrderObserver`, and `OrderService` maintains a list of observers, notifying them upon state changes.

```java
public interface OrderObserver {
    void update(Order order);
}

public class OrderService implements OrderSubject {
    private final List<OrderObserver> observers = new ArrayList<>();
    
    // ... attach/detach methods
    
    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.update(order);
        }
    }
}
```

---

## 4. UML Diagram
![Observer Pattern](observer.png)
(Refer to `observer.puml` for PlantUML source)

---

## 5. Unit Tests
See `tests/` directory for 80%+ coverage, including Happy Path, Edge Cases (Null contact info), and observer detachment tests.

---

## 6. Real-world Example
| Pattern | Business Use Case |
| ------- | ----------------- |
| Observer | Order Tracking    |

Our example implements exactly this use case. The `OrderService` notifies `EmailTracker`, `SmsTracker`, and `MobileAppTracker` when an order's status changes.

---

## 7. Spring Boot Version
See `spring/` directory. The Spring Boot version demonstrates the pattern using Spring's built-in `ApplicationEventPublisher` and `@EventListener`, which is the idiomatic way to implement Observer in Spring for loosely coupled components.

---

## Advantages
* **Open/Closed Principle:** You can introduce new subscriber classes without having to change the publisher's code.
* **Loose Coupling:** The subject only knows that the observer implements a specific interface.
* **Dynamic Subscriptions:** You can establish relations between objects at runtime.

## Disadvantages
* Subscribers are notified in random order (unless specifically ordered).
* Memory leaks can occur if observers are not properly deregistered (lapsed listener problem).

## Use Cases
* Order Tracking Notifications (Email, SMS, Push).
* UI components updating in response to state changes.
* Event handling systems.

## Related Patterns
* **Mediator:** Can be used with Observer to centralize complex communication.
* **Singleton:** Subject is often implemented as a Singleton.
