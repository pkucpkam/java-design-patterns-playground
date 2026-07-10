package behavioral.memento.after;

import java.util.Stack;

public class History {
    private final Stack<Document.Memento> states = new Stack<>();

    public void push(Document.Memento memento) {
        states.push(memento);
    }

    public Document.Memento pop() {
        if (states.isEmpty()) {
            return null;
        }
        return states.pop();
    }
}
