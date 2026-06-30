package structural.flyweight.after;

/**
 * Concrete Flyweight — stores only the <em>intrinsic</em> (shared, immutable) state.
 *
 * <p><b>Intrinsic state</b> (stored here, shared across many particles):
 * <ul>
 *   <li>{@code type}   — logical name used as factory cache key (e.g. "BULLET").</li>
 *   <li>{@code color}  — visual colour string (e.g. "RED").</li>
 *   <li>{@code sprite} — image resource name; in a real game this could be megabytes.</li>
 * </ul>
 *
 * <p><b>Extrinsic state</b> (NOT stored here — passed in at call time):
 * <ul>
 *   <li>Coordinates {@code x}, {@code y} — live in {@link ParticleContext}.</li>
 * </ul>
 *
 * <p>All fields are {@code final} to guarantee thread safety: flyweights can be shared
 * across threads without synchronisation.
 */
public class ConcreteParticleFlyweight implements ParticleFlyweight {

    // ── Intrinsic state — immutable, safe to share ────────────────────────────
    private final String type;    // cache key  (e.g. "BULLET", "MISSILE")
    private final String color;   // render colour
    private final String sprite;  // resource path / heavy object in a real system

    /**
     * Package-private constructor: only {@link ParticleFlyweightFactory} should create instances.
     *
     * @param type   logical particle type (used as cache key)
     * @param color  render colour
     * @param sprite sprite resource identifier
     */
    ConcreteParticleFlyweight(String type, String color, String sprite) {
        if (type == null || type.isBlank())     throw new IllegalArgumentException("type must not be blank");
        if (color == null || color.isBlank())   throw new IllegalArgumentException("color must not be blank");
        if (sprite == null || sprite.isBlank()) throw new IllegalArgumentException("sprite must not be blank");

        this.type   = type;
        this.color  = color;
        this.sprite = sprite;
    }

    /**
     * Draws the particle using the shared (intrinsic) appearance data combined with the
     * caller-supplied (extrinsic) position.
     *
     * @param x extrinsic X coordinate
     * @param y extrinsic Y coordinate
     */
    @Override
    public void draw(double x, double y) {
        System.out.printf("Drawing [type=%-8s | color=%-5s | sprite=%-12s] at (%.1f, %.1f)%n",
                type, color, sprite, x, y);
    }

    @Override
    public String getType() { return type; }

    // ── Package-level accessors (used by tests / factory) ─────────────────────
    String getColor()  { return color;  }
    String getSprite() { return sprite; }

    @Override
    public String toString() {
        return "ConcreteParticleFlyweight{type='%s', color='%s', sprite='%s'}".formatted(type, color, sprite);
    }
}
