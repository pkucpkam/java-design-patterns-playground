package behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component("creditCard")
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Processing credit card payment: $" + amount);
        // Add real logic for credit card payment here
    }
}
