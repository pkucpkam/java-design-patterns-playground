package behavioral.template_method.after;

import behavioral.template_method.Transaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Lớp trừu tượng định nghĩa phương thức mẫu (Template Method).
 */
public abstract class ReportGenerator {
    protected final StringBuilder content = new StringBuilder();

    /**
     * Phương thức mẫu (Template Method) - định nghĩa khung xương thuật toán.
     * Đánh dấu final để ngăn các lớp con thay đổi cấu trúc thuật toán.
     */
    public final void generateReport(List<Transaction> transactions, String outputPath) throws IOException {
        if (!validateData(transactions)) {
            throw new IllegalArgumentException("Dữ liệu giao dịch không hợp lệ!");
        }

        content.setLength(0); // Reset bộ đệm dữ liệu trước khi sinh báo cáo

        formatHeader();
        formatBody(transactions);
        formatFooter();

        double totalAmount = calculateSummary(transactions);
        appendSummary(totalAmount);

        saveReport(outputPath);

        // Sử dụng hook để quyết định xem có gửi thông báo hay không
        if (hookEnableNotification()) {
            sendNotification(outputPath);
        }
    }

    /**
     * Bước mặc định: Kiểm tra dữ liệu. Có thể ghi đè nếu cần thiết.
     */
    protected boolean validateData(List<Transaction> transactions) {
        return transactions != null && !transactions.isEmpty();
    }

    /**
     * Bước mặc định: Tính toán tổng tiền.
     */
    protected double calculateSummary(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Bước mặc định: Gửi thông báo.
     */
    protected void sendNotification(String outputPath) {
        System.out.println("Gửi thông báo: Báo cáo đã được lưu tại " + outputPath);
    }

    /**
     * Hook method: Cho phép lớp con can thiệp vào luồng thuật toán một cách tùy chọn.
     * Mặc định trả về false.
     */
    protected boolean hookEnableNotification() {
        return false;
    }

    // Các bước trừu tượng (Abstract steps) buộc các lớp con phải tự triển khai định dạng cụ thể

    protected abstract void formatHeader();

    protected abstract void formatBody(List<Transaction> transactions);

    protected abstract void formatFooter();

    protected abstract void appendSummary(double total);

    /**
     * Bước ghi file mặc định. Có thể ghi đè nếu muốn xuất ra luồng khác.
     */
    protected void saveReport(String outputPath) throws IOException {
        Files.writeString(Paths.get(outputPath), content.toString());
        System.out.println("Đã lưu báo cáo tại: " + outputPath);
    }
}
