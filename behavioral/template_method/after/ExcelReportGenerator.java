package behavioral.template_method.after;

import behavioral.template_method.Transaction;
import java.util.List;

/**
 * Triển khai cụ thể cho báo cáo định dạng Excel (CSV-like format).
 */
public class ExcelReportGenerator extends ReportGenerator {

    @Override
    protected void formatHeader() {
        content.append("Sheet Name: Transactions\n");
        content.append("ID,Amount,Date,Description\n");
    }

    @Override
    protected void formatBody(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            content.append(String.format("%s,%.2f,%s,%s\n",
                    t.getId(), t.getAmount(), t.getDate(), t.getDescription()));
        }
    }

    @Override
    protected void formatFooter() {
        // Excel không cần chân trang đặc biệt, chỉ kết thúc dữ liệu bảng
    }

    @Override
    protected void appendSummary(double total) {
        content.append(String.format("SUM,,,\nTOTAL: %.2f\n", total));
    }
}
