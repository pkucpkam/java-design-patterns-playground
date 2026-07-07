package behavioral.chain_of_responsibility.after;

public abstract class OrderValidationHandler {
    private OrderValidationHandler nextHandler;

    /**
     * Sets the next handler in the chain.
     * Returns the passed handler to support fluent method chaining.
     */
    public OrderValidationHandler setNext(OrderValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    /**
     * Handle the validation request. Concrete handlers must implement this method.
     */
    public abstract boolean handle(Order order);

    /**
     * Helper method to forward the request to the next handler in the chain.
     * Returns true if there are no more handlers in the chain (reached the end successfully).
     */
    protected boolean checkNext(Order order) {
        if (nextHandler == null) {
            return true;
        }
        return nextHandler.handle(order);
    }
}
