package structural.bridge.tests;

import org.junit.jupiter.api.Test;
import structural.bridge.after.document.Document;
import structural.bridge.after.document.Invoice;
import structural.bridge.after.document.Report;
import structural.bridge.after.format.ExportFormat;
import structural.bridge.after.format.HtmlFormat;
import structural.bridge.after.format.PdfFormat;

import static org.junit.jupiter.api.Assertions.*;

public class BridgePatternTest {

    @Test
    void testPdfInvoice() {
        ExportFormat pdf = new PdfFormat();
        Document invoice = new Invoice(pdf, "Test Invoice");
        
        String result = invoice.export();
        
        assertTrue(result.contains("--- PDF HEADER ---"));
        assertTrue(result.contains("PDF CONTENT: INVOICE - Test Invoice"));
        assertTrue(result.contains("--- PDF FOOTER ---"));
    }

    @Test
    void testHtmlInvoice() {
        ExportFormat html = new HtmlFormat();
        Document invoice = new Invoice(html, "Test Invoice");
        
        String result = invoice.export();
        
        assertTrue(result.contains("<html>"));
        assertTrue(result.contains("<div class=\"content\">INVOICE - Test Invoice</div>"));
        assertTrue(result.contains("</body>"));
    }

    @Test
    void testPdfReport() {
        ExportFormat pdf = new PdfFormat();
        Document report = new Report(pdf, "Test Report");
        
        String result = report.export();
        
        assertTrue(result.contains("--- PDF HEADER ---"));
        assertTrue(result.contains("PDF CONTENT: REPORT - Test Report"));
        assertTrue(result.contains("--- PDF FOOTER ---"));
    }

    @Test
    void testHtmlReport() {
        ExportFormat html = new HtmlFormat();
        Document report = new Report(html, "Test Report");
        
        String result = report.export();
        
        assertTrue(result.contains("<html>"));
        assertTrue(result.contains("<div class=\"content\">REPORT - Test Report</div>"));
        assertTrue(result.contains("</body>"));
    }

    @Test
    void testNullFormatShouldThrowException() {
        assertThrows(NullPointerException.class, () -> {
            Document invoice = new Invoice(null, "Test");
            invoice.export();
        });
    }
}
