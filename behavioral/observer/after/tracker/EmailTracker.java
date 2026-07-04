package behavioral.observer.after.tracker;

import behavioral.observer.after.Order;
import behavioral.observer.after.OrderObserver;

public class EmailTracker implements OrderObserver {
    @Override
    public void update(Order order) {
        if (order.getCustomerEmail() != null && !order.getCustomerEmail().isEmpty()) {
            System.out.println("EmailTracker: Sending email to " + order.getCustomerEmail() + 
                               " | Order " + order.getId() + " status changed to " + order.getStatus());
        }
    }
}
