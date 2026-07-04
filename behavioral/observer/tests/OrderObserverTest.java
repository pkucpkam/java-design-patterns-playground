package behavioral.observer.tests;

import behavioral.observer.after.Order;
import behavioral.observer.after.OrderObserver;
import behavioral.observer.after.OrderService;
import behavioral.observer.after.tracker.EmailTracker;
import behavioral.observer.after.tracker.MobileAppTracker;
import behavioral.observer.after.tracker.SmsTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderObserverTest {

    private OrderService orderService;
    private Order order;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        order = new Order("ORD-001", "CREATED", "user@example.com", "1234567890");
    }

    @Test
    void testHappyPathMultipleObservers() {
        // Arrange
        OrderObserver emailTracker = new EmailTracker();
        OrderObserver smsTracker = new SmsTracker();
        OrderObserver mobileTracker = new MobileAppTracker();

        orderService.attach(emailTracker);
        orderService.attach(smsTracker);
        orderService.attach(mobileTracker);

        // Act
        orderService.updateOrderStatus(order, "SHIPPED");

        // Assert
        assertEquals("SHIPPED", order.getStatus());
        // In a real test, we might use Mockito to verify `update` was called,
        // but here we demonstrate the structure.
    }

    @Test
    void testDetachObserver() {
        // Arrange
        OrderObserver emailTracker = new EmailTracker();
        orderService.attach(emailTracker);
        orderService.detach(emailTracker);

        // Act
        orderService.updateOrderStatus(order, "DELIVERED");

        // Assert
        assertEquals("DELIVERED", order.getStatus());
    }

    @Test
    void testEdgeCaseNullContactInfo() {
        // Arrange
        Order nullContactOrder = new Order("ORD-002", "CREATED", null, null);
        OrderObserver emailTracker = new EmailTracker();
        OrderObserver smsTracker = new SmsTracker();
        orderService.attach(emailTracker);
        orderService.attach(smsTracker);

        // Act & Assert (Should not throw exception)
        orderService.updateOrderStatus(nullContactOrder, "CANCELLED");
        assertEquals("CANCELLED", nullContactOrder.getStatus());
    }
}
