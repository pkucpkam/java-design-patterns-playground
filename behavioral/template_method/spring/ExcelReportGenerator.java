package behavioral.template_method.spring;

import behavioral.template_method.Transaction;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Triển khai cụ thể báo cáo Excel được quản lý như một Spring Bean.
 */
@Component
public class ExcelReportGenerator extends ReportGenerator {

    @Override
    public String getFormatType() {
        return "EXCEL";
    }

    @Override
    protected void formatHeader() {
        content.append("Sheet Name: Transactions [Spring]\n");
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
    }

    @Override
    protected void appendSummary(double total) {
        content.append(String.format("SUM,,,\nTOTAL: %.2f\n", total));
    }
}
