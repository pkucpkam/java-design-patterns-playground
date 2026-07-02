package behavioral.strategy.before;

public class OrderPaymentBefore {
    public void processPayment(String paymentType, int amount) {
        if (paymentType == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        
        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Processing credit card payment of $" + amount);
            // Logic kết nối API ngân hàng, xác thực mã PIN...
        } else if (paymentType.equals("PAYPAL")) {
            System.out.println("Processing PayPal payment of $" + amount);
            // Logic redirect tới PayPal, kiểm tra token...
        } else if (paymentType.equals("CRYPTO")) {
            System.out.println("Processing Crypto payment of $" + amount);
            // Logic kết nối ví blockchain...
        } else {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
