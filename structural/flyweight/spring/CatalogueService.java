package structural.flyweight.spring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Application Service — builds catalogue listing rows using the Flyweight pattern.
 *
 * <p><b>Dependency Injection</b>: {@link ProductFlyweightFactory} is injected via constructor,
 * following Spring best practices (constructor injection over field injection).
 *
 * <p>Scenario: A search result page for "laptop" might return 500 rows all pointing to the
 * same 10 product templates but with different prices and seller IDs.  Without Flyweight,
 * each row would duplicate the product name, category, and thumbnail URL.
 */
@Service
public class CatalogueService {

    private final ProductFlyweightFactory flyweightFactory;

    /** Constructor injection — preferred Spring DI style. */
    public CatalogueService(ProductFlyweightFactory flyweightFactory) {
        this.flyweightFactory = flyweightFactory;
    }

    /**
     * Simulates loading a search result page with multiple listings for the same products
     * sold by different sellers at different prices.
     *
     * @return list of listing contexts ready to be rendered
     */
    public List<ProductListingContext> buildSearchResults() {
        List<ProductListingContext> results = new ArrayList<>();

        // Product templates (intrinsic state) — created once, reused many times
        ProductTemplateFlyweight laptopTemplate = flyweightFactory.getTemplate(
                "PROD-001", "MacBook Pro 16\"", "Laptops", "/images/mbp16.webp");

        ProductTemplateFlyweight mouseTemplate = flyweightFactory.getTemplate(
                "PROD-002", "Logitech MX Master 3", "Peripherals", "/images/mx3.webp");

        ProductTemplateFlyweight laptopAgain = flyweightFactory.getTemplate(
                "PROD-001",                          // ← same productId → cache HIT
                "MacBook Pro 16\"", "Laptops", "/images/mbp16.webp");

        // Verify the factory returns THE SAME instance (reference equality)
        System.out.println("  [Verify] laptopTemplate == laptopAgain? "
                + (laptopTemplate == laptopAgain));

        // Build listing rows (extrinsic state: price + sellerId)
        results.add(new ProductListingContext(laptopTemplate, 2_499.99, "seller-apple-store"));
        results.add(new ProductListingContext(laptopTemplate, 2_349.00, "seller-amazon"));
        results.add(new ProductListingContext(laptopTemplate, 2_399.00, "seller-bestbuy"));
        results.add(new ProductListingContext(mouseTemplate,    99.99, "seller-logitech"));
        results.add(new ProductListingContext(mouseTemplate,    89.00, "seller-amazon"));

        return results;
    }

    /** Renders all search result rows to stdout. */
    public void renderCatalogue(List<ProductListingContext> listings) {
        System.out.println("\n  Product Catalogue:");
        System.out.println("  " + "-".repeat(80));
        listings.forEach(ProductListingContext::render);
        System.out.println("  " + "-".repeat(80));
        System.out.printf("  Listings rendered : %d%n", listings.size());
        System.out.printf("  Unique templates  : %d  ← shared flyweights%n",
                flyweightFactory.cachedCount());
    }
}
