package creational.prototype.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Document implements Prototype<Document> {
    private String title;
    private Author author; // Used to demonstrate deep copy vs shallow copy
    private List<String> tags; // Used to demonstrate deep copy

    public Document() {
    }

    /**
     * Copy constructor. This is the recommended way to perform cloning in Java.
     * Subclasses will call this constructor to copy base class fields.
     */
    protected Document(Document target) {
        if (target != null) {
            this.title = target.title;
            // DEEP COPY: We create a new instance of Author based on the target's author
            if (target.author != null) {
                this.author = new Author(target.author); 
            }
            // DEEP COPY: We create a new List based on the target's list
            if (target.tags != null) {
                this.tags = new ArrayList<>(target.tags);
            }
        }
    }

    // Abstract clone method to be implemented by concrete classes
    @Override
    public abstract Document clone();

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
               (author != null ? author.getName().equals(document.author.getName()) : document.author == null) &&
               Objects.equals(tags, document.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author != null ? author.getName() : null, tags);
    }
}
