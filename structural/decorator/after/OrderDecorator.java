package structural.decorator.after;

/**
 * Abstract Decorator class that maintains a reference to a decorated Order.
 * It delegates all calls to the decorated component.
 */
public abstract class OrderDecorator implements Order {
    protected final Order decoratedOrder;

    protected OrderDecorator(Order decoratedOrder) {
        if (decoratedOrder == null) {
            throw new IllegalArgumentException("Decorated order cannot be null");
        }
        this.decoratedOrder = decoratedOrder;
    }

    @Override
    public double getPrice() {
        return decoratedOrder.getPrice();
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }
}
