package behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component("crypto")
public class CryptoPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Processing Crypto payment: $" + amount);
        // Add real logic for Crypto payment here
    }
}
