package behavioral.state.after;

public class ShippedState implements OrderState {

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
        order.setState(new DeliveredState());
        System.out.println("Order " + order.getId() + " has been successfully delivered.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel shipped order.");
    }

    @Override
    public String getStateName() {
        return "SHIPPED";
    }
}
