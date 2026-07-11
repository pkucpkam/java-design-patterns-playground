package behavioral.iterator.after;

public interface ProductCollection {
    void addProduct(Product product);
    ProductIterator createDefaultIterator();
    ProductIterator createCategoryIterator(String category);
    ProductIterator createDiscountIterator();
}
