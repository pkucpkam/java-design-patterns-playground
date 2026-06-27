package structural.bridge.after.format;

public class HtmlFormat implements ExportFormat {

    @Override
    public String renderHeader() {
        return "<html>\n<body>";
    }

    @Override
    public String renderBody(String content) {
        return "  <div class=\"content\">" + content + "</div>";
    }

    @Override
    public String renderFooter() {
        return "</body>\n</html>";
    }
}
