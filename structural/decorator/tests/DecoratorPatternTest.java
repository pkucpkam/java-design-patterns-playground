package structural.decorator.tests;

import org.junit.jupiter.api.Test;
import structural.decorator.after.*;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorPatternTest {

    @Test
    void testBaseOrder_HappyPath() {
        Order order = new BaseOrder(100.0, "Mechanical Keyboard");
        assertEquals(100.0, order.getPrice(), 0.001);
        assertEquals("Mechanical Keyboard", order.getDescription());
    }

    @Test
    void testVipDiscount_HappyPath() {
        Order order = new BaseOrder(100.0, "Mechanical Keyboard");
        Order vipOrder = new VipDiscountDecorator(order);
        assertEquals(90.0, vipOrder.getPrice(), 0.001);
        assertEquals("Mechanical Keyboard + VIP Discount (10%)", vipOrder.getDescription());
    }

    @Test
    void testBlackFridayDiscount_HappyPath() {
        Order order = new BaseOrder(100.0, "Mechanical Keyboard");
        Order bfOrder = new BlackFridayDiscountDecorator(order);
        assertEquals(80.0, bfOrder.getPrice(), 0.001);
        assertEquals("Mechanical Keyboard + Black Friday Discount (20%)", bfOrder.getDescription());
    }

    @Test
    void testPromoCodeDiscount_HappyPath() {
        Order order = new BaseOrder(100.0, "Mechanical Keyboard");
        Order promoOrder = new PromoCodeDecorator(order, "SAVE20", 20.0);
        assertEquals(80.0, promoOrder.getPrice(), 0.001);
        assertEquals("Mechanical Keyboard + Promo Code SAVE20 (-$20.0)", promoOrder.getDescription());
    }

    @Test
    void testStackedDiscounts_HappyPath() {
        // Apply VIP (10% off) -> then Black Friday (20% off) -> then Promo Code SAVE10 (-$10)
        // Math: 100 * 0.9 = 90 -> 90 * 0.8 = 72 -> 72 - 10 = 62
        Order order = new BaseOrder(100.0, "Mechanical Keyboard");
        Order decorated = new VipDiscountDecorator(order);
        decorated = new BlackFridayDiscountDecorator(decorated);
        decorated = new PromoCodeDecorator(decorated, "SAVE10", 10.0);

        assertEquals(62.0, decorated.getPrice(), 0.001);
        assertEquals(
            "Mechanical Keyboard + VIP Discount (10%) + Black Friday Discount (20%) + Promo Code SAVE10 (-$10.0)", 
            decorated.getDescription()
        );
    }

    @Test
    void testPromoCodePriceCannotBeNegative_EdgeCase() {
        // Price: $15.0. Apply coupon SAVE20 (-$20). Price should be $0.0
        Order order = new BaseOrder(15.0, "Gaming Mouse");
        Order decorated = new PromoCodeDecorator(order, "SAVE20", 20.0);
        assertEquals(0.0, decorated.getPrice(), 0.001);
    }

    @Test
    void testNegativePriceOnBaseOrder_FailureCase() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BaseOrder(-50.0, "Invalid Item");
        }, "Base price cannot be negative");
    }

    @Test
    void testNegativeDiscountValue_FailureCase() {
        Order order = new BaseOrder(50.0, "Item");
        assertThrows(IllegalArgumentException.class, () -> {
            new PromoCodeDecorator(order, "BAD_CODE", -5.0);
        }, "Discount value cannot be negative");
    }

    @Test
    void testNullDecoratedOrder_FailureCase() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VipDiscountDecorator(null);
        });
    }
}
