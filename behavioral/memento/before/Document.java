package behavioral.memento.before;

public class Document {
    private String title;
    private String content;
    private int fontSize;

    public Document(String title, String content, int fontSize) {
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

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }
}
