package behavioral.state.after;

public class Order {
    private final String id;
    private final String customerName;
    private final double amount;
    private OrderState state;

    public Order(String id, String customerName, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        // Default initial state is PendingPaymentState
        this.state = new PendingPaymentState();
    }

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }
}
