package behavioral.chain_of_responsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class PromoCodeValidationHandler implements OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        if (order.getPromoCode() != null && !order.getPromoCode().isBlank()) {
            if (!isValidPromoCode(order.getPromoCode())) {
                System.out.println("Spring Validation failed: Invalid promo code " + order.getPromoCode());
                return false;
            }
        }
        return true;
    }

    private boolean isValidPromoCode(String promoCode) {
        return "SAVE10".equals(promoCode) || "WELCOME50".equals(promoCode);
    }
}
