package structural.bridge.after.format;

public class PdfFormat implements ExportFormat {

    @Override
    public String renderHeader() {
        return "--- PDF HEADER ---";
    }

    @Override
    public String renderBody(String content) {
        return "PDF CONTENT: " + content;
    }

    @Override
    public String renderFooter() {
        return "--- PDF FOOTER ---";
    }
}
