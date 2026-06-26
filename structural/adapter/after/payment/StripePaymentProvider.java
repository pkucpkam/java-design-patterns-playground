package structural.adapter.after.payment;

/**
 * The standard interface that our system expects.
 */
public interface StripePaymentProvider {
    void processPayment(double usdAmount);
}
