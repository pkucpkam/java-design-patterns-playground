package structural.flyweight.spring;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring-managed Flyweight Factory — a singleton {@code @Component} bean.
 *
 * <p>Spring's IoC container ensures only <em>one</em> instance of this factory exists in the
 * application context, which is exactly what we need: the cache is shared globally.
 *
 * <p>In a production system this cache could be backed by Redis or Caffeine; here we use a
 * simple in-memory {@link HashMap} for clarity.
 *
 * <p><b>Dependency Injection</b>: Services that need product templates receive this factory
 * via constructor injection (see {@link CatalogueService}).
 */
@Component
public class ProductFlyweightFactory {

    private final Map<String, ProductTemplateFlyweight> cache = new HashMap<>();

    /**
     * Returns a shared {@link ProductTemplateFlyweight}, creating it on first access.
     *
     * @param productId    unique product identifier (cache key)
     * @param productName  human-readable name (used only on cache miss)
     * @param category     product category  (used only on cache miss)
     * @param thumbnailUrl image URL         (used only on cache miss)
     */
    public ProductTemplateFlyweight getTemplate(String productId, String productName,
                                                 String category, String thumbnailUrl) {
        return cache.computeIfAbsent(productId, id -> {
            System.out.printf("  [Factory] Creating new template for productId='%s'%n", id);
            return new ProductTemplateFlyweight(id, productName, category, thumbnailUrl);
        });
    }

    /** How many distinct product templates are currently cached. */
    public int cachedCount() { return cache.size(); }

    /** Read-only view of the cache (useful for admin endpoints / monitoring). */
    public Map<String, ProductTemplateFlyweight> getCachedTemplates() {
        return Collections.unmodifiableMap(cache);
    }
}
