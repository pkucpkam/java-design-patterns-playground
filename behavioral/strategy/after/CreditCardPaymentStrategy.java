package behavioral.strategy.after;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    
    private final String nameOnCard;
    private final String cardNumber;
    private final String cvv;
    private final String dateOfExpiry;

    public CreditCardPaymentStrategy(String nameOnCard, String cardNumber, String cvv, String dateOfExpiry) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.dateOfExpiry = dateOfExpiry;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Payment failed.");
            return false;
        }
        
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card Holder: " + nameOnCard);
        System.out.println("Card Number: " + maskCardNumber(cardNumber));
        // ... Thực hiện logic gọi API thanh toán với ngân hàng ...
        System.out.println("Credit card payment successful!");
        return true;
    }

    private String maskCardNumber(String number) {
        if (number == null || number.length() < 4) {
            return "****";
        }
        return "****-****-****-" + number.substring(number.length() - 4);
    }
}
