package behavioral.strategy.after;

public class PaymentContext {
    
    private PaymentStrategy strategy;

    /**
     * Client có thể thiết lập strategy lúc khởi tạo
     */
    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Hoặc thay đổi strategy ở runtime (thời gian chạy)
     */
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Ủy quyền việc thanh toán cho đối tượng strategy hiện tại
     */
    public boolean executePayment(double amount) {
        if (this.strategy == null) {
            throw new IllegalStateException("PaymentStrategy is not set.");
        }
        return this.strategy.pay(amount);
    }
}
