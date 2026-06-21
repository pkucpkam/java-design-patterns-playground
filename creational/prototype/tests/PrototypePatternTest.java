package creational.prototype.tests;

import creational.prototype.after.Author;
import creational.prototype.after.Contract;
import creational.prototype.after.DocumentRegistry;
import creational.prototype.after.Report;
import creational.prototype.spring.DocumentService;
import creational.prototype.spring.PrototypeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypePatternTest {

    @Test
    public void testDeepCopyContract() {
        // Arrange
        Contract original = new Contract();
        original.setTitle("Base Contract");
        original.setLegalTerms("Confidentiality Agreement");
        original.setAuthor(new Author("John Doe", "john@example.com"));
        original.setTags(new ArrayList<>(List.of("legal", "base")));

        // Act
        Contract clone = (Contract) original.clone();

        // Assert
        assertNotSame(original, clone, "Clone should be a different instance");
        assertEquals(original, clone, "Clone should have the same data");
        
        // Verify Deep Copy
        assertNotSame(original.getAuthor(), clone.getAuthor(), "Author object should be deep copied");
        assertEquals(original.getAuthor().getName(), clone.getAuthor().getName());

        assertNotSame(original.getTags(), clone.getTags(), "Tags list should be deep copied");
        
        // Mutate original and check clone is unaffected
        original.getAuthor().setName("Jane Doe");
        original.getTags().add("new-tag");
        
        assertNotEquals(original.getAuthor().getName(), clone.getAuthor().getName());
        assertFalse(clone.getTags().contains("new-tag"));
    }

    @Test
    public void testDocumentRegistry() {
        // Arrange
        DocumentRegistry registry = new DocumentRegistry();
        
        // Act
        Report report1 = (Report) registry.getBy("MonthlyReport");
        Report report2 = (Report) registry.getBy("MonthlyReport");

        // Assert
        assertNotNull(report1);
        assertNotNull(report2);
        assertNotSame(report1, report2, "Registry should return new instances (clones)");
        assertEquals(report1.getTitle(), report2.getTitle());
    }

    @Test
    public void testSpringPrototypeScope() {
        // Arrange
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeApplication.class)) {
            DocumentService service = context.getBean(DocumentService.class);

            // Act
            var report1 = service.createNewReport("Header 1");
            var report2 = service.createNewReport("Header 2");

            // Assert
            assertNotNull(report1);
            assertNotNull(report2);
            assertNotSame(report1, report2, "Spring should inject different instances for prototype scope");
            assertEquals("Header 1", report1.getHeader());
            assertEquals("Header 2", report2.getHeader());
        }
    }
}
