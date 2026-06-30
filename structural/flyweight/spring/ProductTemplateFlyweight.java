package structural.flyweight.spring;

import org.springframework.stereotype.Component;

/**
 * Flyweight — represents a shared, immutable product appearance template.
 *
 * <p>In an e-commerce catalogue, thousands of {@link ProductListingContext} items may share
 * the same product template (name, category, thumbnailUrl).  Rather than duplicating this
 * data in every listing row, we cache one {@code ProductTemplateFlyweight} per product type.
 *
 * <p>This bean is <em>not</em> managed directly by Spring; it is created and cached by
 * {@link ProductFlyweightFactory} which IS a Spring-managed component.
 */
public class ProductTemplateFlyweight {

    // Intrinsic state — immutable, shared
    private final String productId;
    private final String productName;
    private final String category;
    private final String thumbnailUrl;

    ProductTemplateFlyweight(String productId, String productName,
                              String category, String thumbnailUrl) {
        this.productId    = productId;
        this.productName  = productName;
        this.category     = category;
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Renders a catalogue row by combining shared template data with per-row extrinsic data.
     *
     * @param price    extrinsic — varies per seller / promotion
     * @param sellerId extrinsic — the merchant offering this product
     */
    public void renderCatalogueRow(double price, String sellerId) {
        System.out.printf("[Catalogue] %-20s | %-15s | $%-8.2f | seller: %s | img: %s%n",
                productName, category, price, sellerId, thumbnailUrl);
    }

    public String getProductId()    { return productId;    }
    public String getProductName()  { return productName;  }
    public String getCategory()     { return category;     }
    public String getThumbnailUrl() { return thumbnailUrl; }

    @Override
    public String toString() {
        return "ProductTemplateFlyweight{id='%s', name='%s'}".formatted(productId, productName);
    }
}
