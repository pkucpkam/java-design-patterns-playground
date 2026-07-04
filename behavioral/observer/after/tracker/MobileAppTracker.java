package behavioral.observer.after.tracker;

import behavioral.observer.after.Order;
import behavioral.observer.after.OrderObserver;

public class MobileAppTracker implements OrderObserver {
    @Override
    public void update(Order order) {
        // Assume all users have the mobile app for this example
        System.out.println("MobileAppTracker: Sending push notification for Order " + order.getId() + 
                           " | New status: " + order.getStatus());
    }
}
