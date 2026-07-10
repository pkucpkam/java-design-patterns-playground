package behavioral.memento.tests;

import behavioral.memento.before.*;
import behavioral.memento.after.*;
import behavioral.memento.spring.*;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MementoPatternTest {

    // =========================================================================
    // 1. TESTS FOR BEFORE REFACTORING
    // =========================================================================

    @Test
    void testBefore_HappyPath_Undo() {
        behavioral.memento.before.Document doc = new behavioral.memento.before.Document(
                "Bản Thảo 1",
                "Nội dung chương 1...",
                12
        );
        behavioral.memento.before.History history = new behavioral.memento.before.History();

        // Lưu trạng thái đầu tiên
        history.save(doc);

        // Chỉnh sửa tài liệu
        doc.setTitle("Bản Thảo 2");
        doc.setContent("Nội dung chương 1 và chương 2...");
        doc.setFontSize(14);

        assertEquals("Bản Thảo 2", doc.getTitle());
        assertEquals("Nội dung chương 1 và chương 2...", doc.getContent());
        assertEquals(14, doc.getFontSize());

        // Thực hiện hoàn tác (Undo)
        history.undo(doc);

        // Đảm bảo trạng thái đã khôi phục về ban đầu
        assertEquals("Bản Thảo 1", doc.getTitle());
        assertEquals("Nội dung chương 1...", doc.getContent());
        assertEquals(12, doc.getFontSize());
    }

    @Test
    void testBefore_EncapsulationViolation() {
        behavioral.memento.before.Document doc = new behavioral.memento.before.Document(
                "Đề Cương",
                "Mục lục chi tiết...",
                11
        );
        behavioral.memento.before.History history = new behavioral.memento.before.History();

        history.save(doc);

        // Lấy trạng thái ra và cố tình chỉnh sửa nội dung trạng thái bên ngoài Document
        // Điều này vi phạm nghiêm trọng tính đóng gói của Document
        behavioral.memento.before.DocumentState state = history.pop();
        assertNotNull(state);

        // Bên ngoài tự ý thay đổi dữ liệu trong State
        state.setTitle("Đề Cương Bị Hack");
        state.setContent("Nội dung đã bị can thiệp!");
        state.setFontSize(30);

        // Đưa state bị chỉnh sửa lại vào history và undo
        history.push(state);
        history.undo(doc);

        // Document bị thay đổi trạng thái thành dữ liệu lỗi/hack mà không tự kiểm soát được
        assertEquals("Đề Cương Bị Hack", doc.getTitle());
        assertEquals("Nội dung đã bị can thiệp!", doc.getContent());
        assertEquals(30, doc.getFontSize());
    }

    // =========================================================================
    // 2. TESTS FOR AFTER REFACTORING (PURE JAVA PATTERN)
    // =========================================================================

    @Test
    void testAfter_HappyPath_Undo() {
        behavioral.memento.after.Document doc = new behavioral.memento.after.Document(
                "Đầu tư tài chính",
                "Phân tích thị trường chứng khoán 2026...",
                13
        );
        behavioral.memento.after.History history = new behavioral.memento.after.History();

        // Lưu trạng thái 1
        history.push(doc.save());

        // Thay đổi tài liệu lần 1
        doc.setTitle("Đầu tư tài chính V2");
        doc.setContent("Phân tích thị trường chứng khoán & bất động sản...");
        doc.setFontSize(14);

        // Lưu trạng thái 2
        history.push(doc.save());

        // Thay đổi tài liệu lần 2
        doc.setTitle("Đầu tư tài chính Final");
        doc.setContent("Báo cáo phân tích đầu tư tổng hợp.");
        doc.setFontSize(16);

        // Kiểm tra trạng thái hiện tại
        assertEquals("Đầu tư tài chính Final", doc.getTitle());

        // Undo lần 1 -> Về V2
        doc.restore(history.pop());
        assertEquals("Đầu tư tài chính V2", doc.getTitle());
        assertEquals("Phân tích thị trường chứng khoán & bất động sản...", doc.getContent());
        assertEquals(14, doc.getFontSize());

        // Undo lần 2 -> Về ban đầu
        doc.restore(history.pop());
        assertEquals("Đầu tư tài chính", doc.getTitle());
        assertEquals("Phân tích thị trường chứng khoán 2026...", doc.getContent());
        assertEquals(13, doc.getFontSize());
    }

    @Test
    void testAfter_EncapsulationIntact() {
        // Kiểm tra thông qua Java Reflection để chứng minh rằng bên ngoài không thể đọc
        // hoặc can thiệp trực tiếp vào thuộc tính của lớp Document.Memento
        Class<?> mementoClass = behavioral.memento.after.Document.Memento.class;

        // 1. Kiểm tra constructor của Memento không phải là public
        Constructor<?>[] constructors = mementoClass.getDeclaredConstructors();
        for (Constructor<?> c : constructors) {
            assertFalse(Modifier.isPublic(c.getModifiers()), "Constructor of Memento should not be public!");
        }

        // 2. Kiểm tra các thuộc tính của Memento đều là private/final và không có public getters
        Field[] fields = mementoClass.getDeclaredFields();
        for (Field f : fields) {
            assertTrue(Modifier.isPrivate(f.getModifiers()), "Field " + f.getName() + " must be private!");
            assertTrue(Modifier.isFinal(f.getModifiers()), "Field " + f.getName() + " should be final (immutable)!");
        }

        // 3. Đảm bảo lớp Memento không cung cấp phương thức public nào ngoại trừ các phương thức kế thừa từ Object
        Method[] methods = mementoClass.getDeclaredMethods();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers())) {
                fail("Memento should not expose any public methods like: " + m.getName());
            }
        }
    }

    // =========================================================================
    // 3. TESTS FOR SPRING BOOT INTEGRATION
    // =========================================================================

    @Test
    void testSpring_HappyPath_Undo() {
        SpringDocumentHistory history = new SpringDocumentHistory();
        DocumentService documentService = new DocumentService(history);

        String docId = "doc-101";

        // 1. Tạo tài liệu mới
        SpringDocument doc = documentService.createDocument(docId, "Spring Note", "Java 21 new features", 12);
        assertNotNull(doc);
        assertEquals(0, history.getHistorySize(docId));

        // 2. Cập nhật tài liệu lần 1 (Sẽ tự động lưu memento trước khi thay đổi)
        documentService.updateDocument(docId, "Spring Note V2", "Java 21 virtual threads", 13);
        assertEquals(1, history.getHistorySize(docId));
        assertEquals("Spring Note V2", doc.getTitle());

        // 3. Cập nhật tài liệu lần 2
        documentService.updateDocument(docId, "Spring Note Final", "Java 21 virtual threads and records", 14);
        assertEquals(2, history.getHistorySize(docId));
        assertEquals("Spring Note Final", doc.getTitle());

        // 4. Thực hiện hoàn tác (Undo) lần 1 -> Về V2
        boolean undo1 = documentService.undo(docId);
        assertTrue(undo1);
        assertEquals(1, history.getHistorySize(docId));
        assertEquals("Spring Note V2", doc.getTitle());
        assertEquals("Java 21 virtual threads", doc.getContent());
        assertEquals(13, doc.getFontSize());

        // 5. Thực hiện hoàn tác (Undo) lần 2 -> Về ban đầu
        boolean undo2 = documentService.undo(docId);
        assertTrue(undo2);
        assertEquals(0, history.getHistorySize(docId));
        assertEquals("Spring Note", doc.getTitle());
        assertEquals("Java 21 new features", doc.getContent());
        assertEquals(12, doc.getFontSize());

        // 6. Hoàn tác lần nữa khi lịch sử trống -> Không thay đổi và trả về false
        boolean undo3 = documentService.undo(docId);
        assertFalse(undo3);
        assertEquals("Spring Note", doc.getTitle()); // Giữ nguyên
    }

    @Test
    void testSpring_MultiDocumentIsolation() {
        SpringDocumentHistory history = new SpringDocumentHistory();
        DocumentService documentService = new DocumentService(history);

        String docA = "doc-A";
        String docB = "doc-B";

        documentService.createDocument(docA, "Doc A", "Content A", 12);
        documentService.createDocument(docB, "Doc B", "Content B", 12);

        // Cập nhật tài liệu A
        documentService.updateDocument(docA, "Doc A Updated", "Content A Updated", 14);

        // Cập nhật tài liệu B
        documentService.updateDocument(docB, "Doc B Updated", "Content B Updated", 15);

        // Đảm bảo lịch sử độc lập
        assertEquals(1, history.getHistorySize(docA));
        assertEquals(1, history.getHistorySize(docB));

        // Undo tài liệu A -> B không bị ảnh hưởng
        documentService.undo(docA);
        assertEquals("Doc A", documentService.getDocument(docA).getTitle());
        assertEquals("Doc B Updated", documentService.getDocument(docB).getTitle());
    }
}
