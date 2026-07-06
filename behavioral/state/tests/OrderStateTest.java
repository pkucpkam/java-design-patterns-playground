package behavioral.state.tests;

import behavioral.state.before.OrderState;
import behavioral.state.after.CancelledState;
import behavioral.state.after.DeliveredState;
import behavioral.state.after.PaidState;
import behavioral.state.after.PendingPaymentState;
import behavioral.state.after.ShippedState;
import behavioral.state.spring.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderStateTest {

    private OrderService springOrderService;

    @BeforeEach
    void setUp() {
        // Khởi tạo các Spring component thủ công để kiểm thử
        List<behavioral.state.spring.OrderState> states = List.of(
                new behavioral.state.spring.PendingPaymentState(),
                new behavioral.state.spring.PaidState(),
                new behavioral.state.spring.ShippedState(),
                new behavioral.state.spring.DeliveredState(),
                new behavioral.state.spring.CancelledState()
        );
        springOrderService = new OrderService(states);
    }

    // ==========================================
    // 1. TESTS FOR BEFORE REFACTORING VERSION
    // ==========================================

    @Test
    void testBefore_HappyPath_CompleteLifecycle() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-001", "Alice", 150.0);
        assertEquals(OrderState.PENDING_PAYMENT, order.getState());

        order.pay();
        assertEquals(OrderState.PAID, order.getState());

        order.ship();
        assertEquals(OrderState.SHIPPED, order.getState());

        order.deliver();
        assertEquals(OrderState.DELIVERED, order.getState());
    }

    @Test
    void testBefore_HappyPath_CancelUnpaidOrder() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-002", "Bob", 80.0);
        order.cancel();
        assertEquals(OrderState.CANCELLED, order.getState());
    }

    @Test
    void testBefore_HappyPath_CancelPaidOrder() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-003", "Charlie", 200.0);
        order.pay();
        order.cancel();
        assertEquals(OrderState.CANCELLED, order.getState());
    }

    @Test
    void testBefore_Failure_ShipUnpaid() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-004", "Alice", 100.0);
        assertThrows(IllegalStateException.class, order::ship);
    }

    @Test
    void testBefore_Failure_DeliverUnshipped() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-005", "Alice", 100.0);
        order.pay();
        assertThrows(IllegalStateException.class, order::deliver);
    }

    @Test
    void testBefore_Failure_CancelShipped() {
        behavioral.state.before.Order order = new behavioral.state.before.Order("ORD-006", "Alice", 100.0);
        order.pay();
        order.ship();
        assertThrows(IllegalStateException.class, order::cancel);
    }

    // ==========================================
    // 2. TESTS FOR AFTER REFACTORING (PATTERN SOLUTION)
    // ==========================================

    @Test
    void testAfter_HappyPath_CompleteLifecycle() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-101", "David", 300.0);
        assertInstanceOf(PendingPaymentState.class, order.getState());
        assertEquals("PENDING_PAYMENT", order.getState().getStateName());

        order.pay();
        assertInstanceOf(PaidState.class, order.getState());
        assertEquals("PAID", order.getState().getStateName());

        order.ship();
        assertInstanceOf(ShippedState.class, order.getState());
        assertEquals("SHIPPED", order.getState().getStateName());

        order.deliver();
        assertInstanceOf(DeliveredState.class, order.getState());
        assertEquals("DELIVERED", order.getState().getStateName());
    }

    @Test
    void testAfter_HappyPath_CancelUnpaidOrder() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-102", "Emma", 50.0);
        order.cancel();
        assertInstanceOf(CancelledState.class, order.getState());
        assertEquals("CANCELLED", order.getState().getStateName());
    }

    @Test
    void testAfter_HappyPath_CancelPaidOrder() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-103", "Frank", 120.0);
        order.pay();
        order.cancel();
        assertInstanceOf(CancelledState.class, order.getState());
    }

    @Test
    void testAfter_Failure_ShipUnpaid() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-104", "David", 300.0);
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::ship);
        assertEquals("Cannot ship unpaid order.", exception.getMessage());
    }

    @Test
    void testAfter_Failure_DeliverUnpaid() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-105", "David", 300.0);
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::deliver);
        assertEquals("Cannot deliver unpaid order.", exception.getMessage());
    }

    @Test
    void testAfter_Failure_DeliverUnshipped() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-106", "David", 300.0);
        order.pay();
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::deliver);
        assertEquals("Order must be shipped before delivery.", exception.getMessage());
    }

    @Test
    void testAfter_Failure_DoublePayment() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-107", "David", 300.0);
        order.pay();
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::pay);
        assertEquals("Order is already paid.", exception.getMessage());
    }

    @Test
    void testAfter_Failure_CancelShipped() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-108", "David", 300.0);
        order.pay();
        order.ship();
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::cancel);
        assertEquals("Cannot cancel shipped order.", exception.getMessage());
    }

    @Test
    void testAfter_Failure_CancelDelivered() {
        behavioral.state.after.Order order = new behavioral.state.after.Order("ORD-109", "David", 300.0);
        order.pay();
        order.ship();
        order.deliver();
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::cancel);
        assertEquals("Cannot cancel delivered order.", exception.getMessage());
    }

    // ==========================================
    // 3. TESTS FOR SPRING BOOT VERSION
    // ==========================================

    @Test
    void testSpring_HappyPath_CompleteLifecycle() {
        behavioral.state.spring.Order order = new behavioral.state.spring.Order("ORD-201", "Grace", 500.0);
        assertEquals("PENDING_PAYMENT", order.getState());

        springOrderService.pay(order);
        assertEquals("PAID", order.getState());

        springOrderService.ship(order);
        assertEquals("SHIPPED", order.getState());

        springOrderService.deliver(order);
        assertEquals("DELIVERED", order.getState());
    }

    @Test
    void testSpring_HappyPath_CancelUnpaidOrder() {
        behavioral.state.spring.Order order = new behavioral.state.spring.Order("ORD-202", "Henry", 40.0);
        springOrderService.cancel(order);
        assertEquals("CANCELLED", order.getState());
    }

    @Test
    void testSpring_Failure_InvalidStateName() {
        behavioral.state.spring.Order order = new behavioral.state.spring.Order("ORD-203", "Invalid", 10.0);
        order.setState("UNKNOWN_STATE");
        assertThrows(IllegalArgumentException.class, () -> springOrderService.pay(order));
    }

    @Test
    void testSpring_Failure_CancelShipped() {
        behavioral.state.spring.Order order = new behavioral.state.spring.Order("ORD-204", "Grace", 500.0);
        springOrderService.pay(order);
        springOrderService.ship(order);
        assertThrows(IllegalStateException.class, () -> springOrderService.cancel(order));
    }
}
