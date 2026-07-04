package behavioral.observer.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringOrderService {
    private final ApplicationEventPublisher eventPublisher;

    public SpringOrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void updateOrderStatus(SpringOrder order, String newStatus) {
        order.setStatus(newStatus);
        // Publish event to notify all listeners (observers)
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order));
    }
}
