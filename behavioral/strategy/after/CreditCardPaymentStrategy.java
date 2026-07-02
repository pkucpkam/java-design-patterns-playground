package behavioral.strategy.after;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;
    
    public CreditCardPaymentStrategy(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid card number");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending in " + cardNumber.substring(cardNumber.length() - 4));
    }
}
