package behavioral.strategy.spring;

public interface PaymentStrategy {
    
    /**
     * Xác định loại thanh toán mà strategy này hỗ trợ
     */
    String getPaymentType();

    /**
     * Thực hiện thanh toán
     */
    boolean pay(double amount);
}
