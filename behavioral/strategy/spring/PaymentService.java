package behavioral.strategy.spring;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PaymentService {
    // Spring Boot will automatically inject all beans implementing PaymentStrategy
    // The map key is the bean name (e.g., "creditCard", "paypal")
    private final Map<String, PaymentStrategy> strategies;

    public PaymentService(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public void processPayment(String method, int amount) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        strategy.pay(amount);
    }
}
