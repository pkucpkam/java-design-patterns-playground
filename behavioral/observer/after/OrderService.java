package behavioral.observer.after;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements OrderSubject {
    private final List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void attach(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.update(order);
        }
    }

    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
        notifyObservers(order);
    }
}
