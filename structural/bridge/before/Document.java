package structural.bridge.before;

public abstract class Document {
    protected String content;
    
    public Document(String content) {
        this.content = content;
    }
    
    public abstract void export();
}
