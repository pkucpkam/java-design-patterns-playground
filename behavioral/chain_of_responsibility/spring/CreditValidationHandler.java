package behavioral.chain_of_responsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class CreditValidationHandler implements OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        double customerBalance = getCustomerBalance(order.getCustomerId());
        if (customerBalance < order.getTotalAmount()) {
            System.out.println("Spring Validation failed: Insufficient balance for customer " + order.getCustomerId());
            return false;
        }
        return true;
    }

    private double getCustomerBalance(String customerId) {
        if ("CUST-POOR".equals(customerId)) {
            return 10.0;
        }
        return 1000.0; // Mock balance
    }
}
