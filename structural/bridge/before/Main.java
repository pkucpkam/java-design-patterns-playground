package structural.bridge.before;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Before Bridge Pattern ===");
        
        Document pdfInvoice = new PdfInvoice("INV-001: $100");
        pdfInvoice.export();
        
        System.out.println();
        
        Document htmlReport = new HtmlReport("Monthly Sales: $5000");
        htmlReport.export();
        
        // As you can see, if we want to add a new format (e.g., CSV) 
        // or a new document type (e.g., Receipt), 
        // we have to create many new classes like CsvInvoice, CsvReport, PdfReceipt, HtmlReceipt...
    }
}
