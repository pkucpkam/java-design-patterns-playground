package behavioral.iterator.tests;

import behavioral.iterator.before.ProductCatalogBefore;
import behavioral.iterator.after.*;
import behavioral.iterator.spring.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorPatternTest {

    private ProductCollection productCollection;

    @BeforeEach
    public void setUp() {
        productCollection = new ProductCollectionImpl();
        productCollection.addProduct(new Product("Laptop", 1200.0, "Electronics", false));
        productCollection.addProduct(new Product("Smartphone", 800.0, "Electronics", true));
        productCollection.addProduct(new Product("Clean Code Book", 40.0, "Books", false));
        productCollection.addProduct(new Product("Headphones", 100.0, "Electronics", true));
    }

    // --- 1. Test cho Before Refactoring ---
    @Test
    public void testBeforeRefactoring_ExposesInternalStructure() {
        ProductCatalogBefore catalog = new ProductCatalogBefore();
        catalog.addProduct(new behavioral.iterator.before.Product("TV", 500, "Electronics", false));

        // Client có quyền truy cập trực tiếp vào List và chỉnh sửa danh sách hoặc duyệt thủ công
        assertNotNull(catalog.getProducts());
        assertEquals(1, catalog.getProducts().size());
        assertEquals("TV", catalog.getProducts().get(0).getName());
    }

    // --- 2. Test cho After Refactoring (Pattern Solution) ---
    
    // Happy Path: Duyệt tuần tự mặc định
    @Test
    public void testPatternSolution_HappyPath_DefaultIterator() {
        ProductIterator iterator = productCollection.createDefaultIterator();
        
        assertTrue(iterator.hasNext());
        assertEquals("Laptop", iterator.next().getName());
        
        assertTrue(iterator.hasNext());
        assertEquals("Smartphone", iterator.next().getName());
        
        assertTrue(iterator.hasNext());
        assertEquals("Clean Code Book", iterator.next().getName());
        
        assertTrue(iterator.hasNext());
        assertEquals("Headphones", iterator.next().getName());
        
        assertFalse(iterator.hasNext());
    }

    // Happy Path: Duyệt theo danh mục
    @Test
    public void testPatternSolution_HappyPath_CategoryIterator() {
        ProductIterator electronicsIterator = productCollection.createCategoryIterator("Electronics");
        
        assertTrue(electronicsIterator.hasNext());
        assertEquals("Laptop", electronicsIterator.next().getName());
        
        assertTrue(electronicsIterator.hasNext());
        assertEquals("Smartphone", electronicsIterator.next().getName());
        
        assertTrue(electronicsIterator.hasNext());
        assertEquals("Headphones", electronicsIterator.next().getName());
        
        assertFalse(electronicsIterator.hasNext());

        ProductIterator booksIterator = productCollection.createCategoryIterator("Books");
        assertTrue(booksIterator.hasNext());
        assertEquals("Clean Code Book", booksIterator.next().getName());
        assertFalse(booksIterator.hasNext());
    }

    // Happy Path: Duyệt sản phẩm giảm giá
    @Test
    public void testPatternSolution_HappyPath_DiscountIterator() {
        ProductIterator discountIterator = productCollection.createDiscountIterator();
        
        assertTrue(discountIterator.hasNext());
        assertEquals("Smartphone", discountIterator.next().getName());
        
        assertTrue(discountIterator.hasNext());
        assertEquals("Headphones", discountIterator.next().getName());
        
        assertFalse(discountIterator.hasNext());
    }

    // Edge Case: Tập hợp rỗng
    @Test
    public void testPatternSolution_EdgeCase_EmptyCollection() {
        ProductCollection emptyCollection = new ProductCollectionImpl();
        
        ProductIterator defaultIt = emptyCollection.createDefaultIterator();
        assertFalse(defaultIt.hasNext());
        
        ProductIterator categoryIt = emptyCollection.createCategoryIterator("Electronics");
        assertFalse(categoryIt.hasNext());
        
        ProductIterator discountIt = emptyCollection.createDiscountIterator();
        assertFalse(discountIt.hasNext());
    }

    // Edge Case: Lọc danh mục không tồn tại
    @Test
    public void testPatternSolution_EdgeCase_CategoryDoesNotExist() {
        ProductIterator iterator = productCollection.createCategoryIterator("Clothing");
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // Edge Case: Tập hợp chứa phần tử null hoặc trường dữ liệu null
    @Test
    public void testPatternSolution_EdgeCase_NullValuesHandling() {
        ProductCollection collectionWithNulls = new ProductCollectionImpl();
        collectionWithNulls.addProduct(null);
        collectionWithNulls.addProduct(new Product("Shirt", 30.0, null, false));
        collectionWithNulls.addProduct(new Product("Hat", 15.0, "Clothing", true));

        // Test category iterator với category có thuộc tính null
        ProductIterator categoryIt = collectionWithNulls.createCategoryIterator("Clothing");
        assertTrue(categoryIt.hasNext());
        assertEquals("Hat", categoryIt.next().getName());
        assertFalse(categoryIt.hasNext());

        // Test discount iterator với phần tử null
        ProductIterator discountIt = collectionWithNulls.createDiscountIterator();
        assertTrue(discountIt.hasNext());
        assertEquals("Hat", discountIt.next().getName());
        assertFalse(discountIt.hasNext());
    }

    // Failure Case: Gọi next() khi hasNext() trả về false
    @Test
    public void testPatternSolution_FailureCase_NoSuchElementException() {
        ProductIterator iterator = productCollection.createDiscountIterator();
        
        // Có 2 sản phẩm giảm giá
        iterator.next();
        iterator.next();
        
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // --- 3. Test cho Spring Boot Integration ---
    @Test
    public void testSpringIntegration_InventoryServiceCalculations() {
        // Mô phỏng Spring DI bằng cách khởi tạo thủ công các Spring Beans
        SpringProductCollection springCollection = new SpringProductCollection();
        SpringInventoryService inventoryService = new SpringInventoryService(springCollection);

        // Dữ liệu mặc định trong SpringProductCollection:
        // 1. Laptop (1000.0, Electronics, false)
        // 2. Smartphone (800.0, Electronics, true)
        // 3. Java Book (50.0, Books, false)
        // 4. Headphones (150.0, Electronics, true)
        
        // Tổng giá trị: 1000 + 800 + 50 + 150 = 2000.0
        assertEquals(2000.0, inventoryService.calculateTotalValue(), 0.001);

        // Giá trị nhóm Electronics: 1000 + 800 + 150 = 1950.0
        assertEquals(1950.0, inventoryService.calculateCategoryValue("Electronics"), 0.001);

        // Giá trị nhóm Books: 50.0
        assertEquals(50.0, inventoryService.calculateCategoryValue("Books"), 0.001);

        // Giá trị các sản phẩm được giảm giá: 800 + 150 = 950.0
        assertEquals(950.0, inventoryService.calculateDiscountedValue(), 0.001);
    }
}
