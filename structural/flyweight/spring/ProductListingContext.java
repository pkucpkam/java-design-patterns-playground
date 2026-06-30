package structural.flyweight.spring;

/**
 * Context — represents a single catalogue listing row (extrinsic state only).
 *
 * <p>Each row knows <em>which</em> product it represents (via a reference to the shared
 * {@link ProductTemplateFlyweight}) and the per-row data: price and seller.
 */
public class ProductListingContext {

    // Extrinsic state — unique per listing
    private final double  price;
    private final String  sellerId;

    // Shared flyweight reference — NOT a copy
    private final ProductTemplateFlyweight template;

    public ProductListingContext(ProductTemplateFlyweight template,
                                  double price, String sellerId) {
        if (template  == null) throw new IllegalArgumentException("template must not be null");
        if (sellerId  == null || sellerId.isBlank())
            throw new IllegalArgumentException("sellerId must not be blank");
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");

        this.template = template;
        this.price    = price;
        this.sellerId = sellerId;
    }

    /** Delegates rendering to the shared template, supplying extrinsic data. */
    public void render() {
        template.renderCatalogueRow(price, sellerId);
    }

    public ProductTemplateFlyweight getTemplate() { return template; }
    public double getPrice()   { return price;    }
    public String getSellerId(){ return sellerId; }
}
