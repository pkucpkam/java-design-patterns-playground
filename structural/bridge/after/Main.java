package structural.bridge.after;

import structural.bridge.after.document.Document;
import structural.bridge.after.document.Invoice;
import structural.bridge.after.document.Report;
import structural.bridge.after.format.ExportFormat;
import structural.bridge.after.format.HtmlFormat;
import structural.bridge.after.format.PdfFormat;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== After Bridge Pattern ===");
        
        ExportFormat pdfFormat = new PdfFormat();
        ExportFormat htmlFormat = new HtmlFormat();
        
        Document pdfInvoice = new Invoice(pdfFormat, "INV-001: $100");
        System.out.println("Exporting Invoice as PDF:");
        System.out.println(pdfInvoice.export());
        System.out.println();
        
        Document htmlReport = new Report(htmlFormat, "Monthly Sales: $5000");
        System.out.println("Exporting Report as HTML:");
        System.out.println(htmlReport.export());
        System.out.println();
        
        // We can easily mix and match without creating new classes!
        Document htmlInvoice = new Invoice(htmlFormat, "INV-002: $200");
        System.out.println("Exporting Invoice as HTML:");
        System.out.println(htmlInvoice.export());
    }
}
