package behavioral.state.spring;

import org.springframework.stereotype.Component;

@Component("cancelledState")
public class CancelledState implements OrderState {

    @Override
    public String getStateName() {
        return "CANCELLED";
    }

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Cannot pay a cancelled order.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship a cancelled order.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a cancelled order.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Order is already cancelled.");
    }
}
