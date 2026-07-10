package behavioral.memento.spring;

public class SpringDocument {
    private final String id;
    private String title;
    private String content;
    private int fontSize;

    public SpringDocument(String id, String title, String content, int fontSize) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.fontSize = fontSize;
    }

    public String getId() {
        return id;
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

    public Memento save() {
        return new Memento(title, content, fontSize);
    }

    public void restore(Memento memento) {
        if (memento != null) {
            this.title = memento.title;
            this.content = memento.content;
            this.fontSize = memento.fontSize;
        }
    }

    @Override
    public String toString() {
        return "SpringDocument{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }

    public static class Memento {
        private final String title;
        private final String content;
        private final int fontSize;

        private Memento(String title, String content, int fontSize) {
            this.title = title;
            this.content = content;
            this.fontSize = fontSize;
        }
    }
}
