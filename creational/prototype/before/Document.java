package creational.prototype.before;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Document {
    private String title;
    private String content;
    private List<String> tags;

    public Document(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(title, document.title) &&
               Objects.equals(content, document.content) &&
               Objects.equals(tags, document.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, tags);
    }
}
