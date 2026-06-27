package structural.bridge.after.format;

public interface ExportFormat {
    String renderHeader();
    String renderBody(String content);
    String renderFooter();
}
