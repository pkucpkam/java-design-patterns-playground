package behavioral.iterator.before;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogBefore {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    // Vi phạm tính Encapsulation: Lộ cấu trúc dữ liệu lưu trữ nội bộ (List) ra ngoài
    public List<Product> getProducts() {
        return products;
    }
}
