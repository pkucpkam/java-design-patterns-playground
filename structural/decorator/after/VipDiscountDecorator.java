package structural.decorator.after;

/**
 * Concrete Decorator that applies a VIP discount (10% off) to the order.
 */
public class VipDiscountDecorator extends OrderDecorator {

    public VipDiscountDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public double getPrice() {
        return super.getPrice() * 0.9;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + VIP Discount (10%)";
    }
}
