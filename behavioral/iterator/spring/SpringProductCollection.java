package behavioral.iterator.spring;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class SpringProductCollection {
    private final List<SpringProduct> products = new ArrayList<>();

    public SpringProductCollection() {
        // Khởi tạo một số dữ liệu mẫu cho Spring Bean
        products.add(new SpringProduct("Laptop", 1000.0, "Electronics", false));
        products.add(new SpringProduct("Smartphone", 800.0, "Electronics", true));
        products.add(new SpringProduct("Java Book", 50.0, "Books", false));
        products.add(new SpringProduct("Headphones", 150.0, "Electronics", true));
    }

    public void addProduct(SpringProduct product) {
        products.add(product);
    }

    public SpringProductIterator createDefaultIterator() {
        return new DefaultProductIterator();
    }

    public SpringProductIterator createCategoryIterator(String category) {
        return new CategoryProductIterator(category);
    }

    public SpringProductIterator createDiscountIterator() {
        return new DiscountProductIterator();
    }

    // --- Concrete Iterator: Mặc định ---
    private class DefaultProductIterator implements SpringProductIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < products.size();
        }

        @Override
        public SpringProduct next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more products available.");
            }
            return products.get(position++);
        }
    }

    // --- Concrete Iterator: Lọc theo danh mục ---
    private class CategoryProductIterator implements SpringProductIterator {
        private final String category;
        private int position = 0;

        public CategoryProductIterator(String category) {
            this.category = category;
        }

        @Override
        public boolean hasNext() {
            while (position < products.size()) {
                SpringProduct product = products.get(position);
                if (product != null && product.getCategory() != null 
                        && product.getCategory().equalsIgnoreCase(category)) {
                    return true;
                }
                position++;
            }
            return false;
        }

        @Override
        public SpringProduct next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more products in category: " + category);
            }
            return products.get(position++);
        }
    }

    // --- Concrete Iterator: Lọc sản phẩm giảm giá ---
    private class DiscountProductIterator implements SpringProductIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            while (position < products.size()) {
                SpringProduct product = products.get(position);
                if (product != null && product.isDiscounted()) {
                    return true;
                }
                position++;
            }
            return false;
        }

        @Override
        public SpringProduct next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more discounted products.");
            }
            return products.get(position++);
        }
    }
}
