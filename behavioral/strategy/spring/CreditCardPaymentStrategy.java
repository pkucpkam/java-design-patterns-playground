package behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            return false;
        }
        System.out.println("Processing Spring Boot credit card payment of $" + amount);
        // Logic thanh toán...
        return true;
    }
}
