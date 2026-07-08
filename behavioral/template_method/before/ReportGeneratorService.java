package behavioral.template_method.before;

import behavioral.template_method.Transaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Lớp dịch vụ sinh báo cáo trước khi refactor.
 * Vi phạm SRP và OCP vì tất cả logic xử lý định dạng khác nhau đều nằm chung một chỗ.
 */
public class ReportGeneratorService {

    public void generateReport(String format, List<Transaction> transactions, String outputPath) throws IOException {
        // 1. Kiểm tra tính hợp lệ của dữ liệu (Trùng lặp logic)
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Danh sách giao dịch không được rỗng!");
        }

        // 2. Thực hiện sinh báo cáo dựa vào định dạng (Dùng if-else cồng kềnh)
        if ("PDF".equalsIgnoreCase(format)) {
            StringBuilder sb = new StringBuilder();
            sb.append("--- PDF REPORT HEADER ---\n");
            sb.append("Generated Date: ").append(java.time.LocalDate.now()).append("\n");
            sb.append("=========================\n");

            for (Transaction t : transactions) {
                sb.append(String.format("ID: %s | Amount: $%.2f | Date: %s | Desc: %s\n",
                        t.getId(), t.getAmount(), t.getDate(), t.getDescription()));
            }

            // Tính tổng tiền (Trùng lặp logic)
            double total = 0;
            for (Transaction t : transactions) {
                total += t.getAmount();
            }
            sb.append("=========================\n");
            sb.append(String.format("TOTAL AMOUNT: $%.2f\n", total));
            sb.append("--- PDF REPORT FOOTER (Page 1 of 1) ---\n");

            // Lưu file (Trùng lặp logic)
            Files.writeString(Paths.get(outputPath), sb.toString());
            System.out.println("Đã lưu báo cáo PDF tại: " + outputPath);

            // Gửi thông báo (Chỉ PDF có)
            System.out.println("Gửi thông báo: Báo cáo PDF đã được lưu tại " + outputPath);

        } else if ("EXCEL".equalsIgnoreCase(format)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sheet Name: Transactions\n");
            sb.append("ID,Amount,Date,Description\n"); // Header excel

            for (Transaction t : transactions) {
                sb.append(String.format("%s,%.2f,%s,%s\n",
                        t.getId(), t.getAmount(), t.getDate(), t.getDescription()));
            }

            // Tính tổng tiền (Trùng lặp logic)
            double total = 0;
            for (Transaction t : transactions) {
                total += t.getAmount();
            }
            sb.append(String.format("SUM,,,\nTOTAL: %.2f\n", total));

            // Lưu file (Trùng lặp logic)
            Files.writeString(Paths.get(outputPath), sb.toString());
            System.out.println("Đã lưu báo cáo Excel tại: " + outputPath);

        } else {
            throw new IllegalArgumentException("Không hỗ trợ định dạng báo cáo: " + format);
        }
    }
}
