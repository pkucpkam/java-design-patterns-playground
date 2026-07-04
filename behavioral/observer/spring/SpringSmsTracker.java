package behavioral.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SpringSmsTracker {
    @EventListener
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        SpringOrder order = event.getOrder();
        if (order.getCustomerPhone() != null && !order.getCustomerPhone().isEmpty()) {
            System.out.println("SpringSmsTracker: Sending SMS to " + order.getCustomerPhone() + 
                               " | Status: " + order.getStatus());
        }
    }
}
