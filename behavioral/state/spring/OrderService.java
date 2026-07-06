package behavioral.state.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Map<String, OrderState> stateMap;

    @Autowired
    public OrderService(List<OrderState> states) {
        this.stateMap = states.stream()
                .collect(Collectors.toMap(OrderState::getStateName, Function.identity()));
    }

    public void pay(Order order) {
        getState(order).pay(order);
    }

    public void ship(Order order) {
        getState(order).ship(order);
    }

    public void deliver(Order order) {
        getState(order).deliver(order);
    }

    public void cancel(Order order) {
        getState(order).cancel(order);
    }

    private OrderState getState(Order order) {
        OrderState state = stateMap.get(order.getState());
        if (state == null) {
            throw new IllegalArgumentException("Unknown state: " + order.getState());
        }
        return state;
    }
}
