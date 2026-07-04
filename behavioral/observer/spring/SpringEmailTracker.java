package behavioral.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SpringEmailTracker {
    @EventListener
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        SpringOrder order = event.getOrder();
        if (order.getCustomerEmail() != null && !order.getCustomerEmail().isEmpty()) {
            System.out.println("SpringEmailTracker: Sending email to " + order.getCustomerEmail() + 
                               " | Status: " + order.getStatus());
        }
    }
}
