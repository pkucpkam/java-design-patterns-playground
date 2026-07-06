package behavioral.state.spring;

public interface OrderState {
    String getStateName();
    void pay(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
}
