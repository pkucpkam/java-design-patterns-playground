package structural.decorator.after;

/**
 * Concrete Component representing a basic order without any discounts applied.
 */
public class BaseOrder implements Order {
    private final double basePrice;
    private final String description;

    public BaseOrder(double basePrice, String description) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        this.basePrice = basePrice;
        this.description = description;
    }

    @Override
    public double getPrice() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
