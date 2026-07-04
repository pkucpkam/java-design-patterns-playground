package behavioral.observer.after.tracker;

import behavioral.observer.after.Order;
import behavioral.observer.after.OrderObserver;

public class SmsTracker implements OrderObserver {
    @Override
    public void update(Order order) {
        if (order.getCustomerPhone() != null && !order.getCustomerPhone().isEmpty()) {
            System.out.println("SmsTracker: Sending SMS to " + order.getCustomerPhone() + 
                               " | Order " + order.getId() + " status changed to " + order.getStatus());
        }
    }
}
