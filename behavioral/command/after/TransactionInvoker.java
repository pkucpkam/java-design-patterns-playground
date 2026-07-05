package behavioral.command.after;

import java.util.Stack;

public class TransactionInvoker {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            throw new IllegalStateException("No operations to undo");
        }
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            throw new IllegalStateException("No operations to redo");
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }

    public int getUndoStackSize() {
        return undoStack.size();
    }

    public int getRedoStackSize() {
        return redoStack.size();
    }
}
