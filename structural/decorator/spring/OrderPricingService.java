package structural.decorator.spring;

import org.springframework.stereotype.Service;
import structural.decorator.after.*;

/**
 * Spring Boot Service that dynamically constructs and applies decorators
 * to a base order based on customer conditions and active promotions.
 */
@Service
public class OrderPricingService {

    /**
     * Calculates the final price by dynamic runtime decoration.
     * 
     * @param basePrice the original price of the order
     * @param description the description of the order
     * @param isVip whether the customer is VIP
     * @param isBlackFriday whether Black Friday campaign is active
     * @param promoCode the coupon code applied
     * @return the decorated Order object
     */
    public Order calculateOrderPrice(double basePrice, String description, boolean isVip, boolean isBlackFriday, String promoCode) {
        Order order = new BaseOrder(basePrice, description);

        // Apply VIP discount if customer is VIP
        if (isVip) {
            order = new VipDiscountDecorator(order);
        }

        // Apply Black Friday discount if campaign is active
        if (isBlackFriday) {
            order = new BlackFridayDiscountDecorator(order);
        }

        // Apply fixed promo code discount
        if (promoCode != null && !promoCode.trim().isEmpty()) {
            if ("SAVE10".equalsIgnoreCase(promoCode)) {
                order = new PromoCodeDecorator(order, "SAVE10", 10.0);
            } else if ("SAVE20".equalsIgnoreCase(promoCode)) {
                order = new PromoCodeDecorator(order, "SAVE20", 20.0);
            }
        }

        return order;
    }
}
