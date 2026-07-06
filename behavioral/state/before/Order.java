package behavioral.state.before;

public class Order {
    private final String id;
    private final String customerName;
    private final double amount;
    private OrderState state;

    public Order(String id, String customerName, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        this.state = OrderState.PENDING_PAYMENT;
    }

    public void pay() {
        switch (state) {
            case PENDING_PAYMENT -> {
                System.out.println("Processing payment of $" + amount + " for order: " + id);
                this.state = OrderState.PAID;
                System.out.println("Order " + id + " has been paid.");
            }
            case PAID -> throw new IllegalStateException("Order is already paid.");
            case SHIPPED -> throw new IllegalStateException("Order is already paid.");
            case DELIVERED -> throw new IllegalStateException("Order is already paid.");
            case CANCELLED -> throw new IllegalStateException("Cannot pay a cancelled order.");
        }
    }

    public void ship() {
        switch (state) {
            case PENDING_PAYMENT -> throw new IllegalStateException("Cannot ship unpaid order.");
            case PAID -> {
                System.out.println("Shipping order: " + id + " to customer " + customerName);
                this.state = OrderState.SHIPPED;
                System.out.println("Order " + id + " is shipped.");
            }
            case SHIPPED -> throw new IllegalStateException("Order is already shipped.");
            case DELIVERED -> throw new IllegalStateException("Order is already shipped.");
            case CANCELLED -> throw new IllegalStateException("Cannot ship a cancelled order.");
        }
    }

    public void deliver() {
        switch (state) {
            case PENDING_PAYMENT -> throw new IllegalStateException("Cannot deliver unpaid order.");
            case PAID -> throw new IllegalStateException("Order must be shipped before delivery.");
            case SHIPPED -> {
                System.out.println("Delivering order: " + id + " to customer " + customerName);
                this.state = OrderState.DELIVERED;
                System.out.println("Order " + id + " has been successfully delivered.");
            }
            case DELIVERED -> throw new IllegalStateException("Order is already delivered.");
            case CANCELLED -> throw new IllegalStateException("Cannot deliver a cancelled order.");
        }
    }

    public void cancel() {
        switch (state) {
            case PENDING_PAYMENT, PAID -> {
                System.out.println("Cancelling order: " + id);
                this.state = OrderState.CANCELLED;
                System.out.println("Order " + id + " has been cancelled.");
            }
            case SHIPPED -> throw new IllegalStateException("Cannot cancel shipped order.");
            case DELIVERED -> throw new IllegalStateException("Cannot cancel delivered order.");
            case CANCELLED -> throw new IllegalStateException("Order is already cancelled.");
        }
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
