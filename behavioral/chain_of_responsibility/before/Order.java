package behavioral.chain_of_responsibility.before;

import java.util.List;

public class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items;
    private final String promoCode;

    public Order(String orderId, String customerId, List<OrderItem> items, String promoCode) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.promoCode = promoCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public double getTotalAmount() {
        if (items == null) {
            return 0.0;
        }
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }
}
