package behavioral.memento.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DocumentService {
    private final SpringDocumentHistory history;
    private final Map<String, SpringDocument> activeDocuments = new ConcurrentHashMap<>();

    @Autowired
    public DocumentService(SpringDocumentHistory history) {
        this.history = history;
    }

    public SpringDocument createDocument(String id, String title, String content, int fontSize) {
        SpringDocument doc = new SpringDocument(id, title, content, fontSize);
        activeDocuments.put(id, doc);
        history.clear(id); // Xoá lịch sử khi tạo mới tài liệu
        return doc;
    }

    public void updateDocument(String id, String newTitle, String newContent, int newFontSize) {
        SpringDocument doc = activeDocuments.get(id);
        if (doc == null) {
            throw new IllegalArgumentException("Document not found: " + id);
        }

        // Lưu trạng thái hiện tại vào lịch sử trước khi sửa đổi (Autosave/Draft)
        history.push(id, doc.save());

        // Cập nhật trạng thái mới
        doc.setTitle(newTitle);
        doc.setContent(newContent);
        doc.setFontSize(newFontSize);
    }

    public boolean undo(String id) {
        SpringDocument doc = activeDocuments.get(id);
        if (doc == null) {
            return false;
        }

        SpringDocument.Memento memento = history.pop(id);
        if (memento != null) {
            doc.restore(memento);
            return true;
        }
        return false;
    }

    public SpringDocument getDocument(String id) {
        return activeDocuments.get(id);
    }
}
