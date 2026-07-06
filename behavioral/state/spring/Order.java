package behavioral.state.spring;

public class Order {
    private final String id;
    private final String customerName;
    private final double amount;
    private String state; // String representing state name (e.g. "PENDING_PAYMENT")

    public Order(String id, String customerName, double amount) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        this.state = "PENDING_PAYMENT";
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
