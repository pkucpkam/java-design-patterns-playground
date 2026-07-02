package behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PaypalPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        // Add real logic for PayPal payment here
    }
}
