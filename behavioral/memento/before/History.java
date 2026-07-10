package behavioral.memento.before;

import java.util.Stack;

public class History {
    private final Stack<DocumentState> states = new Stack<>();

    public void push(DocumentState state) {
        states.push(state);
    }

    public DocumentState pop() {
        if (states.isEmpty()) {
            return null;
        }
        return states.pop();
    }

    // Helper method showing how before-refactoring code saves state
    public void save(Document document) {
        // Viết trực tiếp lấy các thuộc tính cấu trúc nội bộ của Document
        DocumentState state = new DocumentState(
                document.getTitle(),
                document.getContent(),
                document.getFontSize()
        );
        push(state);
    }

    // Helper method showing how before-refactoring code restores state
    public void undo(Document document) {
        DocumentState state = pop();
        if (state != null) {
            // Can thiệp sâu vào việc set các thuộc tính nội bộ của Document
            document.setTitle(state.getTitle());
            document.setContent(state.getContent());
            document.setFontSize(state.getFontSize());
        }
    }
}
