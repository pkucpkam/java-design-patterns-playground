package behavioral.chain_of_responsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class InventoryValidationHandler implements OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        for (OrderItem item : order.getItems()) {
            int availableStock = getStock(item.getProductId());
            if (availableStock < item.getQuantity()) {
                System.out.println("Spring Validation failed: Insufficient stock for product " + item.getProductId() + ". Available: " + availableStock);
                return false;
            }
        }
        return true;
    }

    private int getStock(String productId) {
        if ("PROD-OUT-OF-STOCK".equals(productId)) {
            return 0;
        }
        return 100; // Mock stock count
    }
}
