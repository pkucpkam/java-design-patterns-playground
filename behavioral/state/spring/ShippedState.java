package behavioral.state.spring;

import org.springframework.stereotype.Component;

@Component("shippedState")
public class ShippedState implements OrderState {

    @Override
    public String getStateName() {
        return "SHIPPED";
    }

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already paid.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Order is already shipped.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("Delivering order: " + order.getId() + " to customer " + order.getCustomerName());
        order.setState("DELIVERED");
        System.out.println("Order " + order.getId() + " has been successfully delivered.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel shipped order.");
    }
}
