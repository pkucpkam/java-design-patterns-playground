package structural.bridge.after.document;

import structural.bridge.after.format.ExportFormat;

public abstract class Document {
    protected ExportFormat format;
    protected String content;
    
    public Document(ExportFormat format, String content) {
        this.format = format;
        this.content = content;
    }
    
    public abstract String export();
}
