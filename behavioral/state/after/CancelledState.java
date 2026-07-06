package behavioral.state.after;

public class CancelledState implements OrderState {

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

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
