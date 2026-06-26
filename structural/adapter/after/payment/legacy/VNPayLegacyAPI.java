package structural.adapter.after.payment.legacy;

/**
 * The Adaptee. An older, incompatible API from a 3rd party library.
 * It expects amount in VND and requires a security token.
 */
public class VNPayLegacyAPI {
    public void payViaVNPay(int totalVND, String securityToken) {
        if (totalVND <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        if (securityToken == null || securityToken.isBlank()) {
            throw new IllegalArgumentException("Token is required");
        }
        System.out.println("Processing VNPay: " + totalVND + " VND with token " + securityToken);
    }
}
