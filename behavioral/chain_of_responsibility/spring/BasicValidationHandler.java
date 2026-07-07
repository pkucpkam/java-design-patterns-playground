package behavioral.chain_of_responsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BasicValidationHandler implements OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        if (order == null) {
            System.out.println("Spring Validation failed: Order is null");
            return false;
        }
        if (order.getOrderId() == null || order.getOrderId().isBlank()) {
            System.out.println("Spring Validation failed: Order ID is missing");
            return false;
        }
        if (order.getCustomerId() == null || order.getCustomerId().isBlank()) {
            System.out.println("Spring Validation failed: Customer ID is missing");
            return false;
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            System.out.println("Spring Validation failed: Order items are empty");
            return false;
        }
        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() <= 0) {
                System.out.println("Spring Validation failed: Product " + item.getProductId() + " quantity must be greater than 0");
                return false;
            }
            if (item.getUnitPrice() <= 0) {
                System.out.println("Spring Validation failed: Product " + item.getProductId() + " price must be greater than 0");
                return false;
            }
        }
        return true;
    }
}
