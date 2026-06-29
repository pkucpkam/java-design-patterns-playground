package structural.decorator.after;

/**
 * Concrete Decorator that applies a fixed promo code discount to the order.
 * Ensures that the final price cannot be negative.
 */
public class PromoCodeDecorator extends OrderDecorator {
    private final String promoCode;
    private final double discountValue;

    public PromoCodeDecorator(Order decoratedOrder, String promoCode, double discountValue) {
        super(decoratedOrder);
        if (discountValue < 0) {
            throw new IllegalArgumentException("Discount value cannot be negative");
        }
        this.promoCode = promoCode;
        this.discountValue = discountValue;
    }

    @Override
    public double getPrice() {
        return Math.max(0.0, super.getPrice() - discountValue);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Promo Code " + promoCode + " (-$" + discountValue + ")";
    }
}
