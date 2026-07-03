package behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component
public class PaypalPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentType() {
        return "PAYPAL";
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            return false;
        }
        System.out.println("Processing Spring Boot PayPal payment of $" + amount);
        // Logic thanh toán...
        return true;
    }
}
