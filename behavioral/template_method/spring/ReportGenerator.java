package behavioral.template_method.spring;

import behavioral.template_method.Transaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Lớp trừu tượng định nghĩa phương thức mẫu (Template Method) tương thích với Spring Boot.
 */
public abstract class ReportGenerator {
    protected final StringBuilder content = new StringBuilder();

    /**
     * Trả về loại định dạng báo cáo (ví dụ: PDF, EXCEL).
     * Được dùng để map động trong Spring Service.
     */
    public abstract String getFormatType();

    /**
     * Phương thức mẫu (Template Method) - định nghĩa khung xương thuật toán.
     */
    public final void generateReport(List<Transaction> transactions, String outputPath) throws IOException {
        if (!validateData(transactions)) {
            throw new IllegalArgumentException("Dữ liệu giao dịch không hợp lệ!");
        }

        content.setLength(0); // Reset bộ đệm dữ liệu

        formatHeader();
        formatBody(transactions);
        formatFooter();

        double totalAmount = calculateSummary(transactions);
        appendSummary(totalAmount);

        saveReport(outputPath);

        if (hookEnableNotification()) {
            sendNotification(outputPath);
        }
    }

    protected boolean validateData(List<Transaction> transactions) {
        return transactions != null && !transactions.isEmpty();
    }

    protected double calculateSummary(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    protected void sendNotification(String outputPath) {
        System.out.println("Gửi thông báo [Spring]: Báo cáo đã được lưu tại " + outputPath);
    }

    protected boolean hookEnableNotification() {
        return false;
    }

    protected abstract void formatHeader();

    protected abstract void formatBody(List<Transaction> transactions);

    protected abstract void formatFooter();

    protected abstract void appendSummary(double total);

    protected void saveReport(String outputPath) throws IOException {
        Files.writeString(Paths.get(outputPath), content.toString());
        System.out.println("Đã lưu báo cáo [Spring] tại: " + outputPath);
    }
}
