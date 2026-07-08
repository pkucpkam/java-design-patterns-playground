package behavioral.template_method.after;

import behavioral.template_method.Transaction;
import java.util.List;

/**
 * Triển khai cụ thể cho báo cáo định dạng PDF.
 */
public class PdfReportGenerator extends ReportGenerator {

    @Override
    protected void formatHeader() {
        content.append("--- PDF REPORT HEADER ---\n");
        content.append("Generated Date: ").append(java.time.LocalDate.now()).append("\n");
        content.append("=========================\n");
    }

    @Override
    protected void formatBody(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            content.append(String.format("ID: %s | Amount: $%.2f | Date: %s | Desc: %s\n",
                    t.getId(), t.getAmount(), t.getDate(), t.getDescription()));
        }
    }

    @Override
    protected void formatFooter() {
        content.append("=========================\n");
    }

    @Override
    protected void appendSummary(double total) {
        content.append(String.format("TOTAL AMOUNT: $%.2f\n", total));
        content.append("--- PDF REPORT FOOTER (Page 1 of 1) ---\n");
    }

    @Override
    protected boolean hookEnableNotification() {
        // PDF báo cáo quan trọng, kích hoạt gửi thông báo email/SMS
        return true;
    }
}
