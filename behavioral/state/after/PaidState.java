package behavioral.state.after;

public class PaidState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already paid.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("Shipping order: " + order.getId() + " to customer " + order.getCustomerName());
        order.setState(new ShippedState());
        System.out.println("Order " + order.getId() + " is shipped.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Order must be shipped before delivery.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Processing refund of $" + order.getAmount() + " for order: " + order.getId());
        order.setState(new CancelledState());
        System.out.println("Order " + order.getId() + " has been refunded and cancelled.");
    }

    @Override
    public String getStateName() {
        return "PAID";
    }
}
