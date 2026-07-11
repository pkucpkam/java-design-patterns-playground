package behavioral.iterator.after;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductCollectionImpl implements ProductCollection {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public ProductIterator createDefaultIterator() {
        return new DefaultProductIterator();
    }

    @Override
    public ProductIterator createCategoryIterator(String category) {
        return new CategoryProductIterator(category);
    }

    @Override
    public ProductIterator createDiscountIterator() {
        return new DiscountProductIterator();
    }

    // --- Concrete Iterator: Duyệt tuần tự qua tất cả sản phẩm ---
    private class DefaultProductIterator implements ProductIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < products.size();
        }

        @Override
        public Product next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more products in the catalog.");
            }
            return products.get(position++);
        }
    }

    // --- Concrete Iterator: Lọc và duyệt sản phẩm theo danh mục ---
    private class CategoryProductIterator implements ProductIterator {
        private final String category;
        private int position = 0;

        public CategoryProductIterator(String category) {
            this.category = category;
        }

        @Override
        public boolean hasNext() {
            while (position < products.size()) {
                Product product = products.get(position);
                if (product != null && product.getCategory() != null 
                        && product.getCategory().equalsIgnoreCase(category)) {
                    return true;
                }
                position++;
            }
            return false;
        }

        @Override
        public Product next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more products in this category: " + category);
            }
            return products.get(position++);
        }
    }

    // --- Concrete Iterator: Lọc và duyệt qua sản phẩm đang giảm giá ---
    private class DiscountProductIterator implements ProductIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            while (position < products.size()) {
                Product product = products.get(position);
                if (product != null && product.isDiscounted()) {
                    return true;
                }
                position++;
            }
            return false;
        }

        @Override
        public Product next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more discounted products.");
            }
            return products.get(position++);
        }
    }
}
