package behavioral.chain_of_responsibility.after;

public class InventoryValidationHandler extends OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        for (OrderItem item : order.getItems()) {
            int availableStock = getStock(item.getProductId());
            if (availableStock < item.getQuantity()) {
                System.out.println("Validation failed: Insufficient stock for product " + item.getProductId() + ". Available: " + availableStock);
                return false;
            }
        }
        return checkNext(order);
    }

    private int getStock(String productId) {
        if ("PROD-OUT-OF-STOCK".equals(productId)) {
            return 0;
        }
        return 100; // Mock stock count
    }
}
