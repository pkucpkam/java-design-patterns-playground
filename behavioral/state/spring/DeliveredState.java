package behavioral.state.spring;

import org.springframework.stereotype.Component;

@Component("deliveredState")
public class DeliveredState implements OrderState {

    @Override
    public String getStateName() {
        return "DELIVERED";
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
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel delivered order.");
    }
}
