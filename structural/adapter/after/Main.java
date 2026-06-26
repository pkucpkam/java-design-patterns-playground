package structural.adapter.after;

import structural.adapter.after.payment.StripePaymentProvider;
import structural.adapter.after.payment.adapter.VNPayAdapter;
import structural.adapter.after.payment.legacy.VNPayLegacyAPI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Client: I can work fine with the Target interface (StripePaymentProvider).");

        // We could have a Stripe implementation
        // StripePaymentProvider stripe = new StandardStripeProvider();
        // stripe.processPayment(100.0);

        // But we want to use VNPay API which is incompatible
        VNPayLegacyAPI adaptee = new VNPayLegacyAPI();
        
        // We use the Adapter to make it compatible
        StripePaymentProvider adapter = new VNPayAdapter(adaptee);
        
        System.out.println("\nClient: Using the adapter to process payment...");
        // Client only knows about `processPayment(double usdAmount)`
        // The adapter translates this to `payViaVNPay(int totalVND, String securityToken)`
        adapter.processPayment(10.50); // 10.50 USD
    }
}
