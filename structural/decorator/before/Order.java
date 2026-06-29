package structural.decorator.before;

/**
 * Problematic Order class that calculates its final price and description.
 * It contains logic for all discounts inside the class itself.
 * 
 * Violated Principles:
 * - Single Responsibility Principle (SRP): Responsible for order attributes AND price calculation rules.
 * - Open/Closed Principle (OCP): Any new discount type requires modifying this file.
 */
public class Order {
    private final double basePrice;
    private final String description;
    private final boolean isVip;
    private final String promoCode;
    private final boolean isBlackFriday;

    public Order(double basePrice, String description, boolean isVip, String promoCode, boolean isBlackFriday) {
        this.basePrice = basePrice;
        this.description = description;
        this.isVip = isVip;
        this.promoCode = promoCode;
        this.isBlackFriday = isBlackFriday;
    }

    public double calculatePrice() {
        double price = basePrice;

        // Apply VIP discount (10% off)
        if (isVip) {
            price *= 0.9;
        }

        // Apply Black Friday discount (20% off)
        if (isBlackFriday) {
            price *= 0.8;
        }

        // Apply Promo Code discount
        if ("SAVE10".equalsIgnoreCase(promoCode)) {
            price = Math.max(0, price - 10.0);
        } else if ("SAVE20".equalsIgnoreCase(promoCode)) {
            price = Math.max(0, price - 20.0);
        }

        return price;
    }

    public String getDescription() {
        StringBuilder desc = new StringBuilder(description);
        if (isVip) {
            desc.append(" + VIP Discount (10%)");
        }
        if (isBlackFriday) {
            desc.append(" + Black Friday Discount (20%)");
        }
        if (promoCode != null && !promoCode.trim().isEmpty()) {
            double value = 0.0;
            if ("SAVE10".equalsIgnoreCase(promoCode)) {
                value = 10.0;
            } else if ("SAVE20".equalsIgnoreCase(promoCode)) {
                value = 20.0;
            }
            desc.append(" + Promo Code ").append(promoCode).append(" (-$").append(value).append(")");
        }
        return desc.toString();
    }
}
