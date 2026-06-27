package structural.bridge.before;

public class HtmlReport extends Document {
    
    public HtmlReport(String content) {
        super(content);
    }

    @Override
    public void export() {
        System.out.println("Exporting Report as HTML:");
        System.out.println("<html><body>");
        System.out.println("<h1>Report Data: " + content + "</h1>");
        System.out.println("</body></html>");
    }
}
