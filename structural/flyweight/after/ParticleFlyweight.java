package structural.flyweight.after;

/**
 * Flyweight interface — declares the operation that accepts extrinsic state at call time.
 *
 * <p>The key design decision: instead of storing mutable, context-specific data inside the
 * flyweight object, we pass that data as method arguments ({@code x} and {@code y}).
 * This keeps the flyweight itself <em>stateless</em> (or read-only), allowing safe sharing.
 */
public interface ParticleFlyweight {

    /**
     * Renders this particle type at the given position.
     *
     * @param x the X coordinate of this specific particle instance (extrinsic state)
     * @param y the Y coordinate of this specific particle instance (extrinsic state)
     */
    void draw(double x, double y);

    /** Returns the particle type name used as the cache key in the factory. */
    String getType();
}
