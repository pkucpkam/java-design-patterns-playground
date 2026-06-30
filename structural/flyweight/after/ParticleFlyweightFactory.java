package structural.flyweight.after;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Factory — the heart of the Flyweight Pattern.
 *
 * <p>Responsibilities:
 * <ol>
 *   <li>Acts as the single point for obtaining {@link ParticleFlyweight} objects.</li>
 *   <li>Maintains an internal cache ({@code Map}) keyed by particle type.</li>
 *   <li>Creates a new flyweight <em>only</em> when the requested type is not yet in cache;
 *       otherwise returns the existing shared instance.</li>
 * </ol>
 *
 * <p>This is the classic <em>object pool / cache</em> that makes Flyweight memory-efficient:
 * no matter how many bullet particles the game spawns, only <b>one</b>
 * {@code ConcreteParticleFlyweight} with type "BULLET" ever exists in memory.
 *
 * <p>Thread safety: The current implementation is single-threaded for clarity.
 * In a concurrent system use {@link java.util.concurrent.ConcurrentHashMap} and
 * {@code computeIfAbsent} to make it lock-free.
 */
public class ParticleFlyweightFactory {

    /** Cache: type → shared flyweight object. */
    private final Map<String, ParticleFlyweight> cache = new HashMap<>();

    /**
     * Returns the shared {@link ParticleFlyweight} for the given type, creating it on first use.
     *
     * @param type   particle type key (e.g. "BULLET", "MISSILE")
     * @param color  colour used only when creating a NEW flyweight
     * @param sprite sprite used only when creating a NEW flyweight
     * @return the shared flyweight — always the same instance for the same {@code type}
     */
    public ParticleFlyweight getFlyweight(String type, String color, String sprite) {
        // computeIfAbsent: atomically insert only if key is absent
        return cache.computeIfAbsent(type,
                key -> {
                    System.out.printf("  [Factory] Cache MISS → creating new flyweight for type='%s'%n", key);
                    return new ConcreteParticleFlyweight(key, color, sprite);
                });
    }

    /**
     * Returns an unmodifiable view of the internal cache for inspection / testing.
     *
     * @return read-only map of all cached flyweights
     */
    public Map<String, ParticleFlyweight> getCachedFlyweights() {
        return Collections.unmodifiableMap(cache);
    }

    /** Convenience method: how many distinct flyweight types are currently cached. */
    public int cachedCount() {
        return cache.size();
    }
}
