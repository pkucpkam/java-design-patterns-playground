package behavioral.memento.after;

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

    // Tạo bản lưu trạng thái (Memento)
    public Memento save() {
        return new Memento(title, content, fontSize);
    }

    // Khôi phục trạng thái từ Memento
    public void restore(Memento memento) {
        if (memento != null) {
            // Lớp Document bên ngoài có quyền truy cập trực tiếp vào các thuộc tính private của inner class Memento
            this.title = memento.title;
            this.content = memento.content;
            this.fontSize = memento.fontSize;
        }
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }

    // Static nested class đóng gói trạng thái của Document
    public static class Memento {
        private final String title;
        private final String content;
        private final int fontSize;

        // Constructor private: Chỉ Document mới có thể khởi tạo đối tượng Memento
        private Memento(String title, String content, int fontSize) {
            this.title = title;
            this.content = content;
            this.fontSize = fontSize;
        }
        
        // Không định nghĩa bất kỳ public getter/setter nào để bảo vệ dữ liệu khỏi bên ngoài (Caretaker)
    }
}
