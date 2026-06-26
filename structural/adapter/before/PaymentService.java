package structural.adapter.before;

// Simulated external legacy API class
class VNPayLegacyAPI {
    public void payViaVNPay(int totalVND, String securityToken) {
        System.out.println("Processing VNPay: " + totalVND + " VND with token " + securityToken);
    }
}

public class PaymentService {
    private VNPayLegacyAPI vnpayApi;

    public PaymentService() {
        this.vnpayApi = new VNPayLegacyAPI();
    }

    // The logic is tightly coupled with VNPayLegacyAPI.
    // If we want to add Stripe, we have to modify this method significantly with if/else.
    public void checkout(String paymentMethod, double usdAmount) {
        if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
            // Business logic must handle the conversion itself, violating SRP
            int amountInVND = (int) (usdAmount * 25000); 
            String token = "TOKEN-" + System.currentTimeMillis();
            
            // Direct dependency on legacy API
            vnpayApi.payViaVNPay(amountInVND, token);
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}
