package behavioral.strategy.after;

public class CryptoPaymentStrategy implements PaymentStrategy {
    private final String walletAddress;

    public CryptoPaymentStrategy(String walletAddress) {
        if (walletAddress == null || walletAddress.isEmpty()) {
            throw new IllegalArgumentException("Invalid wallet address");
        }
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Crypto wallet: " + walletAddress);
    }
}
