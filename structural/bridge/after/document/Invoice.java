package structural.bridge.after.document;

import structural.bridge.after.format.ExportFormat;

public class Invoice extends Document {

    public Invoice(ExportFormat format, String content) {
        super(format, content);
    }

    @Override
    public String export() {
        StringBuilder sb = new StringBuilder();
        sb.append(format.renderHeader()).append("\n");
        sb.append(format.renderBody("INVOICE - " + content)).append("\n");
        sb.append(format.renderFooter());
        return sb.toString();
    }
}
