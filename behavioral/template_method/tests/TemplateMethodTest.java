package behavioral.template_method.tests;

import behavioral.template_method.Transaction;
import behavioral.template_method.before.ReportGeneratorService;
import behavioral.template_method.after.PdfReportGenerator;
import behavioral.template_method.after.ExcelReportGenerator;
import behavioral.template_method.spring.ReportExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Các ca kiểm thử cho mẫu thiết kế Template Method.
 */
class TemplateMethodTest {

    @TempDir
    Path tempDir;

    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        transactions = List.of(
                new Transaction("TX-001", 150.0, LocalDate.of(2026, 7, 1), "Thanh toan hoa don dien"),
                new Transaction("TX-002", 49.99, LocalDate.of(2026, 7, 2), "Mua sach online"),
                new Transaction("TX-003", 2500.0, LocalDate.of(2026, 7, 3), "Nhan luong thang 6")
        );
    }

    // =========================================================================
    // 1. TESTS FOR BEFORE REFACTORING
    // =========================================================================

    @Test
    void testBefore_HappyPath_Pdf() throws IOException {
        ReportGeneratorService service = new ReportGeneratorService();
        Path outputPath = tempDir.resolve("before_report.pdf");

        service.generateReport("PDF", transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("PDF REPORT HEADER"));
        assertTrue(content.contains("TX-001"));
        assertTrue(content.contains("TOTAL AMOUNT: $2699.99"));
    }

    @Test
    void testBefore_HappyPath_Excel() throws IOException {
        ReportGeneratorService service = new ReportGeneratorService();
        Path outputPath = tempDir.resolve("before_report.csv");

        service.generateReport("EXCEL", transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("Sheet Name: Transactions"));
        assertTrue(content.contains("TX-002,49.99"));
        assertTrue(content.contains("TOTAL: 2699.99"));
    }

    @Test
    void testBefore_Failure_EmptyData() {
        ReportGeneratorService service = new ReportGeneratorService();
        Path outputPath = tempDir.resolve("error.pdf");

        assertThrows(IllegalArgumentException.class, () ->
                service.generateReport("PDF", Collections.emptyList(), outputPath.toString())
        );
    }

    @Test
    void testBefore_Failure_UnsupportedFormat() {
        ReportGeneratorService service = new ReportGeneratorService();
        Path outputPath = tempDir.resolve("error.txt");

        assertThrows(IllegalArgumentException.class, () ->
                service.generateReport("XML", transactions, outputPath.toString())
        );
    }

    // =========================================================================
    // 2. TESTS FOR AFTER REFACTORING (PURE JAVA PATTERN)
    // =========================================================================

    @Test
    void testAfter_HappyPath_Pdf() throws IOException {
        PdfReportGenerator generator = new PdfReportGenerator();
        Path outputPath = tempDir.resolve("after_report.pdf");

        generator.generateReport(transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("PDF REPORT HEADER"));
        assertTrue(content.contains("TOTAL AMOUNT: $2699.99"));
    }

    @Test
    void testAfter_HappyPath_Excel() throws IOException {
        ExcelReportGenerator generator = new ExcelReportGenerator();
        Path outputPath = tempDir.resolve("after_report.csv");

        generator.generateReport(transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("Sheet Name: Transactions"));
        assertTrue(content.contains("TOTAL: 2699.99"));
    }

    @Test
    void testAfter_Failure_NullData() {
        PdfReportGenerator generator = new PdfReportGenerator();
        Path outputPath = tempDir.resolve("error.pdf");

        assertThrows(IllegalArgumentException.class, () ->
                generator.generateReport(null, outputPath.toString())
        );
    }

    // =========================================================================
    // 3. TESTS FOR SPRING BOOT INTEGRATION
    // =========================================================================

    @Test
    void testSpring_HappyPath_Pdf() throws IOException {
        var pdfGen = new behavioral.template_method.spring.PdfReportGenerator();
        var excelGen = new behavioral.template_method.spring.ExcelReportGenerator();
        ReportExportService exportService = new ReportExportService(List.of(pdfGen, excelGen));

        Path outputPath = tempDir.resolve("spring_report.pdf");
        exportService.exportReport("PDF", transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("PDF REPORT HEADER [Spring]"));
        assertTrue(content.contains("TOTAL AMOUNT: $2699.99"));
    }

    @Test
    void testSpring_HappyPath_Excel() throws IOException {
        var pdfGen = new behavioral.template_method.spring.PdfReportGenerator();
        var excelGen = new behavioral.template_method.spring.ExcelReportGenerator();
        ReportExportService exportService = new ReportExportService(List.of(pdfGen, excelGen));

        Path outputPath = tempDir.resolve("spring_report.csv");
        exportService.exportReport("EXCEL", transactions, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertTrue(content.contains("Sheet Name: Transactions [Spring]"));
        assertTrue(content.contains("TOTAL: 2699.99"));
    }

    @Test
    void testSpring_Failure_UnsupportedFormat() {
        var pdfGen = new behavioral.template_method.spring.PdfReportGenerator();
        var excelGen = new behavioral.template_method.spring.ExcelReportGenerator();
        ReportExportService exportService = new ReportExportService(List.of(pdfGen, excelGen));

        Path outputPath = tempDir.resolve("spring_report.xml");
        assertThrows(IllegalArgumentException.class, () ->
                exportService.exportReport("XML", transactions, outputPath.toString())
        );
    }
}
