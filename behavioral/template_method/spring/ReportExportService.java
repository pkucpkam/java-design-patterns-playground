package behavioral.template_method.spring;

import behavioral.template_method.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Lớp dịch vụ Spring quản lý danh sách các ReportGenerator và định tuyến yêu cầu.
 */
@Service
public class ReportExportService {

    private final Map<String, ReportGenerator> generatorMap;

    @Autowired
    public ReportExportService(List<ReportGenerator> generators) {
        // Tự động gom các beans có kiểu ReportGenerator vào Map theo key của định dạng
        this.generatorMap = generators.stream()
                .collect(Collectors.toMap(
                        g -> g.getFormatType().toUpperCase(),
                        Function.identity()
                ));
    }

    /**
     * Xuất báo cáo theo định dạng yêu cầu bằng generator thích hợp.
     */
    public void exportReport(String format, List<Transaction> transactions, String outputPath) throws IOException {
        ReportGenerator generator = generatorMap.get(format.toUpperCase());
        if (generator == null) {
            throw new IllegalArgumentException("Không tìm thấy bộ sinh báo cáo định dạng: " + format);
        }
        generator.generateReport(transactions, outputPath);
    }
}
