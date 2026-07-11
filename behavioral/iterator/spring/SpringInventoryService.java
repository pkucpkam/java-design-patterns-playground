package behavioral.iterator.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringInventoryService {

    private final SpringProductCollection collection;

    @Autowired
    public SpringInventoryService(SpringProductCollection collection) {
        this.collection = collection;
    }

    public double calculateTotalValue() {
        double total = 0;
        SpringProductIterator iterator = collection.createDefaultIterator();
        while (iterator.hasNext()) {
            total += iterator.next().getPrice();
        }
        return total;
    }

    public double calculateCategoryValue(String category) {
        double total = 0;
        SpringProductIterator iterator = collection.createCategoryIterator(category);
        while (iterator.hasNext()) {
            total += iterator.next().getPrice();
        }
        return total;
    }

    public double calculateDiscountedValue() {
        double total = 0;
        SpringProductIterator iterator = collection.createDiscountIterator();
        while (iterator.hasNext()) {
            total += iterator.next().getPrice();
        }
        return total;
    }
}
