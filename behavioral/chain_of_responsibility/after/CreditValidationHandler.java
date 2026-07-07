package behavioral.chain_of_responsibility.after;

public class CreditValidationHandler extends OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        double customerBalance = getCustomerBalance(order.getCustomerId());
        if (customerBalance < order.getTotalAmount()) {
            System.out.println("Validation failed: Insufficient balance for customer " + order.getCustomerId());
            return false;
        }
        return checkNext(order);
    }

    private double getCustomerBalance(String customerId) {
        if ("CUST-POOR".equals(customerId)) {
            return 10.0;
        }
        return 1000.0; // Mock balance
    }
}
