package structural.adapter.after.payment.adapter;

import structural.adapter.after.payment.StripePaymentProvider;
import structural.adapter.after.payment.legacy.VNPayLegacyAPI;

/**
 * The Adapter. It makes the Adaptee compatible with the Target interface.
 */
public class VNPayAdapter implements StripePaymentProvider {
    private final VNPayLegacyAPI vnPayLegacyAPI;
    private static final double EXCHANGE_RATE = 25000.0;

    public VNPayAdapter(VNPayLegacyAPI vnPayLegacyAPI) {
        this.vnPayLegacyAPI = vnPayLegacyAPI;
    }

    @Override
    public void processPayment(double usdAmount) {
        // Convert USD to VND
        int vndAmount = (int) Math.round(usdAmount * EXCHANGE_RATE);
        
        // Generate a token for the legacy API
        String token = "VNPAY-TOKEN-" + System.currentTimeMillis();
        
        // Delegate to the Adaptee
        vnPayLegacyAPI.payViaVNPay(vndAmount, token);
    }
}
