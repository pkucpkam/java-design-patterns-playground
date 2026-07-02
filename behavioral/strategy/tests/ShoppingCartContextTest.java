package behavioral.strategy.tests;

import behavioral.strategy.after.CreditCardPaymentStrategy;
import behavioral.strategy.after.PaypalPaymentStrategy;
import behavioral.strategy.after.ShoppingCartContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartContextTest {

    @Test
    void testCheckoutWithCreditCard() {
        ShoppingCartContext context = new ShoppingCartContext();
        context.setPaymentStrategy(new CreditCardPaymentStrategy("1234567812345678"));
        
        // Ensure no exception is thrown
        assertDoesNotThrow(() -> context.checkout(100));
    }

    @Test
    void testCheckoutWithPaypal() {
        ShoppingCartContext context = new ShoppingCartContext();
        context.setPaymentStrategy(new PaypalPaymentStrategy("user@example.com"));
        
        // Ensure no exception is thrown
        assertDoesNotThrow(() -> context.checkout(200));
    }

    @Test
    void testCheckoutWithoutStrategyThrowsException() {
        ShoppingCartContext context = new ShoppingCartContext();
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> context.checkout(100));
        assertEquals("Payment strategy not set", exception.getMessage());
    }
    
    @Test
    void testInvalidCreditCardThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new CreditCardPaymentStrategy("123"));
    }
    
    @Test
    void testInvalidPaypalThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new PaypalPaymentStrategy("invalid-email"));
    }
}
