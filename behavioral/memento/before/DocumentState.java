package behavioral.memento.before;

public class DocumentState {
    private String title;
    private String content;
    private int fontSize;

    public DocumentState(String title, String content, int fontSize) {
        this.title = title;
        this.content = content;
        this.fontSize = fontSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
