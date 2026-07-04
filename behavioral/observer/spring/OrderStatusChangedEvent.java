package behavioral.observer.spring;

import org.springframework.context.ApplicationEvent;

public class OrderStatusChangedEvent extends ApplicationEvent {
    private final SpringOrder order;

    public OrderStatusChangedEvent(Object source, SpringOrder order) {
        super(source);
        this.order = order;
    }

    public SpringOrder getOrder() {
        return order;
    }
}
