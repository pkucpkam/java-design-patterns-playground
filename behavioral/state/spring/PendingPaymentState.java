package behavioral.state.spring;

import org.springframework.stereotype.Component;

@Component("pendingPaymentState")
public class PendingPaymentState implements OrderState {

    @Override
    public String getStateName() {
        return "PENDING_PAYMENT";
    }

    @Override
    public void pay(Order order) {
        System.out.println("Processing Spring Boot payment of $" + order.getAmount() + " for order: " + order.getId());
        order.setState("PAID");
        System.out.println("Order " + order.getId() + " has been paid.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship unpaid order.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver unpaid order.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Cancelling unpaid order: " + order.getId());
        order.setState("CANCELLED");
        System.out.println("Order " + order.getId() + " has been cancelled.");
    }
}
