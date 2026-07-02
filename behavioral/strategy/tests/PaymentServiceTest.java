package behavioral.strategy.tests;

import behavioral.strategy.spring.CryptoPaymentStrategy;
import behavioral.strategy.spring.PaymentService;
import behavioral.strategy.spring.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        Map<String, PaymentStrategy> strategies = new HashMap<>();
        strategies.put("crypto", new CryptoPaymentStrategy());
        // For testing we just inject one or two
        paymentService = new PaymentService(strategies);
    }

    @Test
    void testProcessExistingPaymentMethod() {
        assertDoesNotThrow(() -> paymentService.processPayment("crypto", 500));
    }

    @Test
    void testProcessNonExistingPaymentMethodThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> paymentService.processPayment("unknown", 100));
            
        assertEquals("Unsupported payment method: unknown", exception.getMessage());
    }
}
