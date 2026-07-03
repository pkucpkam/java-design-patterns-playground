package behavioral.strategy.after;

public interface PaymentStrategy {
    /**
     * Thực hiện thanh toán với số tiền cụ thể
     *
     * @param amount số tiền cần thanh toán
     * @return true nếu thanh toán thành công, ngược lại false
     */
    boolean pay(double amount);
}
