package structural.decorator.after;

/**
 * Concrete Decorator that applies a Black Friday discount (20% off) to the order.
 */
public class BlackFridayDiscountDecorator extends OrderDecorator {

    public BlackFridayDiscountDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public double getPrice() {
        return super.getPrice() * 0.8;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Black Friday Discount (20%)";
    }
}
