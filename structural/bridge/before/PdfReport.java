package structural.bridge.before;

public class PdfReport extends Document {
    
    public PdfReport(String content) {
        super(content);
    }

    @Override
    public void export() {
        System.out.println("Exporting Report as PDF:");
        System.out.println("--- PDF HEADER ---");
        System.out.println("Report Data: " + content);
        System.out.println("--- PDF FOOTER ---");
    }
}
