package behavioral.chain_of_responsibility.after;

public class PromoCodeValidationHandler extends OrderValidationHandler {

    @Override
    public boolean handle(Order order) {
        if (order.getPromoCode() != null && !order.getPromoCode().isBlank()) {
            if (!isValidPromoCode(order.getPromoCode())) {
                System.out.println("Validation failed: Invalid promo code " + order.getPromoCode());
                return false;
            }
        }
        return checkNext(order);
    }

    private boolean isValidPromoCode(String promoCode) {
        return "SAVE10".equals(promoCode) || "WELCOME50".equals(promoCode);
    }
}
