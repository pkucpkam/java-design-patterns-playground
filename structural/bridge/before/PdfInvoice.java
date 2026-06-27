package structural.bridge.before;

public class PdfInvoice extends Document {
    
    public PdfInvoice(String content) {
        super(content);
    }

    @Override
    public void export() {
        System.out.println("Exporting Invoice as PDF:");
        System.out.println("--- PDF HEADER ---");
        System.out.println("Invoice Data: " + content);
        System.out.println("--- PDF FOOTER ---");
    }
}
