package structural.bridge.before;

public class HtmlInvoice extends Document {
    
    public HtmlInvoice(String content) {
        super(content);
    }

    @Override
    public void export() {
        System.out.println("Exporting Invoice as HTML:");
        System.out.println("<html><body>");
        System.out.println("<h1>Invoice Data: " + content + "</h1>");
        System.out.println("</body></html>");
    }
}
