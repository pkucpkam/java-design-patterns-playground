package behavioral.chain_of_responsibility.spring;

public interface OrderValidationHandler {
    /**
     * Performs validation on the order.
     * Returns true if the order is valid for this step, false otherwise.
     */
    boolean handle(Order order);
}
