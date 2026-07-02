package behavioral.strategy.after;

public class PaypalPaymentStrategy implements PaymentStrategy {
    private final String email;

    public PaypalPaymentStrategy(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using PayPal account: " + email);
    }
}
