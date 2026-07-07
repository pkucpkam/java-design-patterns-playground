package behavioral.chain_of_responsibility.tests;

import behavioral.chain_of_responsibility.before.OrderValidationService;
import behavioral.chain_of_responsibility.after.OrderValidationHandler;
import behavioral.chain_of_responsibility.after.BasicValidationHandler;
import behavioral.chain_of_responsibility.after.InventoryValidationHandler;
import behavioral.chain_of_responsibility.after.PromoCodeValidationHandler;
import behavioral.chain_of_responsibility.after.CreditValidationHandler;
import behavioral.chain_of_responsibility.spring.OrderValidationPipeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainOfResponsibilityTest {

    private OrderValidationService beforeService;
    private OrderValidationHandler afterChain;
    private OrderValidationPipeline springPipeline;

    @BeforeEach
    void setUp() {
        // 1. Setup BEFORE Refactoring Service
        beforeService = new OrderValidationService();

        // 2. Setup AFTER Refactoring Chain (Pure Java)
        // BasicValidationHandler -> InventoryValidationHandler -> PromoCodeValidationHandler -> CreditValidationHandler
        afterChain = new BasicValidationHandler();
        afterChain.setNext(new InventoryValidationHandler())
                  .setNext(new PromoCodeValidationHandler())
                  .setNext(new CreditValidationHandler());

        // 3. Setup SPRING Boot Pipeline (manually wired for test simplicity)
        List<behavioral.chain_of_responsibility.spring.OrderValidationHandler> springHandlers = List.of(
                new behavioral.chain_of_responsibility.spring.BasicValidationHandler(),
                new behavioral.chain_of_responsibility.spring.InventoryValidationHandler(),
                new behavioral.chain_of_responsibility.spring.PromoCodeValidationHandler(),
                new behavioral.chain_of_responsibility.spring.CreditValidationHandler()
        );
        springPipeline = new OrderValidationPipeline(springHandlers);
    }

    // =========================================================================
    // 1. TESTS FOR BEFORE REFACTORING
    // =========================================================================

    @Test
    void testBefore_HappyPath_ValidOrder() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-001", 2, 50.0));
        var order = new behavioral.chain_of_responsibility.before.Order("ORD-001", "CUST-001", items, "SAVE10");

        assertTrue(beforeService.validate(order));
    }

    @Test
    void testBefore_EdgeCase_NoPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-001", 1, 100.0));
        var order = new behavioral.chain_of_responsibility.before.Order("ORD-002", "CUST-001", items, null);

        assertTrue(beforeService.validate(order));
    }

    @Test
    void testBefore_Failure_MissingOrderId() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-001", 1, 100.0));
        var order = new behavioral.chain_of_responsibility.before.Order("", "CUST-001", items, null);

        assertFalse(beforeService.validate(order));
    }

    @Test
    void testBefore_Failure_OutOfStock() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-OUT-OF-STOCK", 1, 10.0));
        var order = new behavioral.chain_of_responsibility.before.Order("ORD-003", "CUST-001", items, null);

        assertFalse(beforeService.validate(order));
    }

    @Test
    void testBefore_Failure_InvalidPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-001", 1, 50.0));
        var order = new behavioral.chain_of_responsibility.before.Order("ORD-004", "CUST-001", items, "INVALID_PROMO");

        assertFalse(beforeService.validate(order));
    }

    @Test
    void testBefore_Failure_InsufficientBalance() {
        var items = List.of(new behavioral.chain_of_responsibility.before.OrderItem("PROD-001", 5, 200.0)); // Total 1000
        var order = new behavioral.chain_of_responsibility.before.Order("ORD-005", "CUST-POOR", items, null);

        assertFalse(beforeService.validate(order));
    }

    // =========================================================================
    // 2. TESTS FOR AFTER REFACTORING (PURE JAVA PATTERN)
    // =========================================================================

    @Test
    void testAfter_HappyPath_ValidOrder() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-001", 2, 50.0));
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-101", "CUST-001", items, "SAVE10");

        assertTrue(afterChain.handle(order));
    }

    @Test
    void testAfter_EdgeCase_NoPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-001", 1, 100.0));
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-102", "CUST-001", items, "");

        assertTrue(afterChain.handle(order));
    }

    @Test
    void testAfter_Failure_MissingCustomerId() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-001", 1, 100.0));
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-103", "   ", items, null);

        assertFalse(afterChain.handle(order));
    }

    @Test
    void testAfter_Failure_OutOfStock() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-OUT-OF-STOCK", 5, 20.0));
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-104", "CUST-001", items, null);

        assertFalse(afterChain.handle(order));
    }

    @Test
    void testAfter_Failure_InvalidPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-001", 1, 50.0));
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-105", "CUST-001", items, "WELCOME100");

        assertFalse(afterChain.handle(order));
    }

    @Test
    void testAfter_Failure_InsufficientBalance() {
        var items = List.of(new behavioral.chain_of_responsibility.after.OrderItem("PROD-001", 2, 10.0)); // Total 20 > Poor balance 10
        var order = new behavioral.chain_of_responsibility.after.Order("ORD-106", "CUST-POOR", items, null);

        assertFalse(afterChain.handle(order));
    }

    // =========================================================================
    // 3. TESTS FOR SPRING BOOT INTEGRATION
    // =========================================================================

    @Test
    void testSpring_HappyPath_ValidOrder() {
        var items = List.of(new behavioral.chain_of_responsibility.spring.OrderItem("PROD-001", 3, 30.0));
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-201", "CUST-001", items, "WELCOME50");

        assertTrue(springPipeline.validate(order));
    }

    @Test
    void testSpring_EdgeCase_NoPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.spring.OrderItem("PROD-001", 2, 40.0));
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-202", "CUST-001", items, null);

        assertTrue(springPipeline.validate(order));
    }

    @Test
    void testSpring_Failure_EmptyItems() {
        List<behavior.chain_of_responsibility.spring.OrderItem> items = List.of();
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-203", "CUST-001", items, null);

        assertFalse(springPipeline.validate(order));
    }

    @Test
    void testSpring_Failure_OutOfStock() {
        var items = List.of(new behavioral.chain_of_responsibility.spring.OrderItem("PROD-OUT-OF-STOCK", 1, 5.0));
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-204", "CUST-001", items, null);

        assertFalse(springPipeline.validate(order));
    }

    @Test
    void testSpring_Failure_InvalidPromoCode() {
        var items = List.of(new behavioral.chain_of_responsibility.spring.OrderItem("PROD-001", 1, 50.0));
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-205", "CUST-001", items, "DISCOUNT90");

        assertFalse(springPipeline.validate(order));
    }

    @Test
    void testSpring_Failure_InsufficientBalance() {
        var items = List.of(new behavioral.chain_of_responsibility.spring.OrderItem("PROD-001", 1, 15.0)); // Total 15 > Poor balance 10
        var order = new behavioral.chain_of_responsibility.spring.Order("ORD-206", "CUST-POOR", items, null);

        assertFalse(springPipeline.validate(order));
    }
}
