package behavioral.strategy.after;

public class PaypalPaymentStrategy implements PaymentStrategy {
    
    private final String email;
    private final String password;

    public PaypalPaymentStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Payment failed.");
            return false;
        }

        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("PayPal Account: " + email);
        // ... Thực hiện logic xác thực và thanh toán qua PayPal API ...
        System.out.println("PayPal payment successful!");
        return true;
    }
}
