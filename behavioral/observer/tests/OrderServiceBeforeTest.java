package behavioral.observer.tests;

import behavioral.observer.before.Order;
import behavioral.observer.before.OrderService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceBeforeTest {

    @Test
    void testUpdateOrderStatus() {
        OrderService service = new OrderService();
        Order order = new Order("123", "NEW", "test@test.com", "123456789");
        
        service.updateOrderStatus(order, "SHIPPED");
        
        assertEquals("SHIPPED", order.getStatus());
        // Since EmailTracker and SmsTracker just print to console, we can't easily assert
        // their behavior here without mocking System.out, but we test the happy path execution.
    }
}
