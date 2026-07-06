package behavioral.state.after;

public class PendingPaymentState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("Processing payment of $" + order.getAmount() + " for order: " + order.getId());
        order.setState(new PaidState());
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
        order.setState(new CancelledState());
        System.out.println("Order " + order.getId() + " has been cancelled.");
    }

    @Override
    public String getStateName() {
        return "PENDING_PAYMENT";
    }
}
