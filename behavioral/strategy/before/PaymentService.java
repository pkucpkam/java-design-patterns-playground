package behavioral.strategy.before;

public class PaymentService {

    public void processPayment(String paymentType, double amount, String... details) {
        if ("CREDIT_CARD".equalsIgnoreCase(paymentType)) {
            // Lấy thông tin thẻ
            String cardNumber = details.length > 0 ? details[0] : "";
            String cvv = details.length > 1 ? details[1] : "";
            System.out.println("Processing credit card payment of $" + amount);
            System.out.println("Card: " + cardNumber + " - CVV: " + cvv);
            // ... Logic xử lý API với ngân hàng ...
            System.out.println("Credit card payment successful!");

        } else if ("PAYPAL".equalsIgnoreCase(paymentType)) {
            // Lấy thông tin paypal
            String email = details.length > 0 ? details[0] : "";
            String password = details.length > 1 ? details[1] : "";
            System.out.println("Processing PayPal payment of $" + amount);
            System.out.println("Email: " + email);
            // ... Logic xử lý với PayPal API ...
            System.out.println("PayPal payment successful!");

        } else if ("APPLE_PAY".equalsIgnoreCase(paymentType)) {
            // Apple Pay logic
            String deviceId = details.length > 0 ? details[0] : "";
            System.out.println("Processing Apple Pay payment of $" + amount);
            System.out.println("Device ID: " + deviceId);
            // ... Logic kết nối Apple Pay ...
            System.out.println("Apple Pay payment successful!");

        } else {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
