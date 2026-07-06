package behavioral.state.after;

public class DeliveredState implements OrderState {

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

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}
