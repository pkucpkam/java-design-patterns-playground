package behavioral.memento.spring;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringDocumentHistory {
    // Quản lý lịch sử hoàn tác riêng biệt cho từng documentId
    private final Map<String, Stack<SpringDocument.Memento>> historyMap = new ConcurrentHashMap<>();

    public void push(String documentId, SpringDocument.Memento memento) {
        historyMap.computeIfAbsent(documentId, k -> new Stack<>()).push(memento);
    }

    public SpringDocument.Memento pop(String documentId) {
        Stack<SpringDocument.Memento> stack = historyMap.get(documentId);
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    public void clear(String documentId) {
        historyMap.remove(documentId);
    }

    public int getHistorySize(String documentId) {
        Stack<SpringDocument.Memento> stack = historyMap.get(documentId);
        return stack != null ? stack.size() : 0;
    }
}
