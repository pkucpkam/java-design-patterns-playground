package behavioral.chain_of_responsibility.before;

public class OrderValidationService {

    public boolean validate(Order order) {
        if (order == null) {
            System.out.println("Validation failed: Order is null");
            return false;
        }

        // 1. Basic validation
        if (order.getOrderId() == null || order.getOrderId().isBlank()) {
            System.out.println("Validation failed: Order ID is missing");
            return false;
        }
        if (order.getCustomerId() == null || order.getCustomerId().isBlank()) {
            System.out.println("Validation failed: Customer ID is missing");
            return false;
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            System.out.println("Validation failed: Order items are empty");
            return false;
        }
        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() <= 0) {
                System.out.println("Validation failed: Product " + item.getProductId() + " quantity must be greater than 0");
                return false;
            }
            if (item.getUnitPrice() <= 0) {
                System.out.println("Validation failed: Product " + item.getProductId() + " price must be greater than 0");
                return false;
            }
        }

        // 2. Inventory validation
        for (OrderItem item : order.getItems()) {
            int availableStock = getStock(item.getProductId());
            if (availableStock < item.getQuantity()) {
                System.out.println("Validation failed: Insufficient stock for product " + item.getProductId() + ". Available: " + availableStock);
                return false;
            }
        }

        // 3. Promo code validation
        if (order.getPromoCode() != null && !order.getPromoCode().isBlank()) {
            if (!isValidPromoCode(order.getPromoCode())) {
                System.out.println("Validation failed: Invalid promo code " + order.getPromoCode());
                return false;
            }
        }

        // 4. Credit/Balance validation
        double customerBalance = getCustomerBalance(order.getCustomerId());
        if (customerBalance < order.getTotalAmount()) {
            System.out.println("Validation failed: Insufficient balance for customer " + order.getCustomerId());
            return false;
        }

        System.out.println("Order " + order.getOrderId() + " has passed all validations.");
        return true;
    }

    private int getStock(String productId) {
        if ("PROD-OUT-OF-STOCK".equals(productId)) {
            return 0;
        }
        return 100; // Mock stock count
    }

    private boolean isValidPromoCode(String promoCode) {
        return "SAVE10".equals(promoCode) || "WELCOME50".equals(promoCode);
    }

    private double getCustomerBalance(String customerId) {
        if ("CUST-POOR".equals(customerId)) {
            return 10.0;
        }
        return 1000.0; // Mock balance
    }
}
