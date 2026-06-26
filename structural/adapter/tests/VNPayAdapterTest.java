package structural.adapter.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structural.adapter.after.payment.adapter.VNPayAdapter;
import structural.adapter.after.payment.legacy.VNPayLegacyAPI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VNPayAdapterTest {
    private VNPayAdapter adapter;
    private VNPayLegacyAPI adaptee;

    @BeforeEach
    void setUp() {
        adaptee = new VNPayLegacyAPI();
        adapter = new VNPayAdapter(adaptee);
    }

    @Test
    void testProcessPayment_HappyPath() {
        // Should process payment without throwing exceptions
        assertDoesNotThrow(() -> adapter.processPayment(10.0));
    }

    @Test
    void testProcessPayment_EdgeCase_ZeroAmount() {
        // VNPayLegacyAPI throws IllegalArgumentException on amount <= 0
        assertThrows(IllegalArgumentException.class, () -> adapter.processPayment(0.0));
    }

    @Test
    void testProcessPayment_EdgeCase_NegativeAmount() {
        // VNPayLegacyAPI throws IllegalArgumentException on amount <= 0
        assertThrows(IllegalArgumentException.class, () -> adapter.processPayment(-5.0));
    }
}
