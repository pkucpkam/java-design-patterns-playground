package behavioral.strategy.tests;

import behavioral.strategy.after.CreditCardPaymentStrategy;
import behavioral.strategy.after.PaymentContext;
import behavioral.strategy.after.PaymentStrategy;
import behavioral.strategy.after.PaypalPaymentStrategy;
import behavioral.strategy.before.PaymentService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentStrategyTest {

    // --- Test cho Before Refactoring ---

    @Test
    public void testBeforeRefactoring_HappyPath_CreditCard() {
        PaymentService service = new PaymentService();
        assertDoesNotThrow(() -> {
            service.processPayment("CREDIT_CARD", 100.0, "1234567890123456", "123");
        });
    }

    @Test
    public void testBeforeRefactoring_HappyPath_Paypal() {
        PaymentService service = new PaymentService();
        assertDoesNotThrow(() -> {
            service.processPayment("PAYPAL", 50.0, "test@example.com", "password123");
        });
    }

    @Test
    public void testBeforeRefactoring_FailureCase_Unsupported() {
        PaymentService service = new PaymentService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.processPayment("BITCOIN", 200.0);
        });
        assertTrue(exception.getMessage().contains("Unsupported payment type"));
    }

    // --- Test cho After Refactoring (Pattern Solution) ---

    @Test
    public void testPatternSolution_HappyPath_CreditCard() {
        PaymentStrategy strategy = new CreditCardPaymentStrategy("John Doe", "1234567890123456", "123", "12/25");
        PaymentContext context = new PaymentContext(strategy);
        
        boolean result = context.executePayment(150.0);
        assertTrue(result, "Payment should be successful");
    }

    @Test
    public void testPatternSolution_HappyPath_Paypal() {
        PaymentStrategy strategy = new PaypalPaymentStrategy("user@example.com", "secure123");
        PaymentContext context = new PaymentContext(strategy);
        
        boolean result = context.executePayment(250.0);
        assertTrue(result, "Payment should be successful");
    }

    @Test
    public void testPatternSolution_EdgeCase_NegativeAmount() {
        PaymentStrategy strategy = new PaypalPaymentStrategy("user@example.com", "secure123");
        PaymentContext context = new PaymentContext(strategy);
        
        boolean result = context.executePayment(-10.0);
        assertFalse(result, "Payment should fail for negative amount");
    }

    @Test
    public void testPatternSolution_EdgeCase_ZeroAmount() {
        PaymentStrategy strategy = new CreditCardPaymentStrategy("Jane Doe", "1111222233334444", "999", "11/26");
        PaymentContext context = new PaymentContext(strategy);
        
        boolean result = context.executePayment(0.0);
        assertFalse(result, "Payment should fail for zero amount");
    }

    @Test
    public void testPatternSolution_EdgeCase_NullCardNumber() {
        PaymentStrategy strategy = new CreditCardPaymentStrategy("No Name", null, "000", "01/01");
        PaymentContext context = new PaymentContext(strategy);
        
        boolean result = context.executePayment(10.0);
        assertTrue(result, "Payment should succeed even if card number is null (due to simple masking logic)");
    }

    @Test
    public void testPatternSolution_FailureCase_NoStrategySet() {
        PaymentContext context = new PaymentContext(null);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            context.executePayment(100.0);
        });
        assertEquals("PaymentStrategy is not set.", exception.getMessage());
    }

    @Test
    public void testPatternSolution_ChangeStrategyAtRuntime() {
        PaymentContext context = new PaymentContext(new PaypalPaymentStrategy("email@test.com", "pass"));
        assertTrue(context.executePayment(50.0));
        
        // Đổi strategy lúc runtime
        context.setPaymentStrategy(new CreditCardPaymentStrategy("Name", "1234", "123", "01/30"));
        assertTrue(context.executePayment(100.0));
    }
}
