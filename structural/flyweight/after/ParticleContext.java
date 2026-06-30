package structural.flyweight.after;

/**
 * Context — stores the <em>extrinsic</em> (unique, mutable) state of one particle instance.
 *
 * <p>Instead of owning a copy of color and sprite, the Context holds only a <em>reference</em>
 * to the shared {@link ParticleFlyweight}.  When rendering is needed, it delegates the
 * drawing call to the flyweight and passes its own coordinates as arguments.
 *
 * <p>Memory layout comparison (conceptual):
 * <pre>
 *   Before (no Flyweight):
 *     Particle { color="RED", sprite="bullet.png", x=120.0, y=340.0 }  ← full copy per instance
 *
 *   After (Flyweight):
 *     ParticleContext { flyweight=&lt;shared ref&gt;, x=120.0, y=340.0 }     ← only position stored
 *     ConcreteParticleFlyweight { color="RED", sprite="bullet.png" }    ← ONE shared instance
 * </pre>
 */
public class ParticleContext {

    // ── Extrinsic state (unique per particle) ─────────────────────────────────
    private double x;
    private double y;

    // ── Reference to shared flyweight (NOT a copy) ────────────────────────────
    private final ParticleFlyweight flyweight;

    /**
     * Creates a context for one logical particle instance.
     *
     * @param flyweight shared flyweight that carries the intrinsic appearance data
     * @param x         initial X coordinate
     * @param y         initial Y coordinate
     * @throws IllegalArgumentException if flyweight is null
     */
    public ParticleContext(ParticleFlyweight flyweight, double x, double y) {
        if (flyweight == null) throw new IllegalArgumentException("flyweight must not be null");
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
    }

    /**
     * Renders this particle by delegating to the shared flyweight, supplying extrinsic coords.
     */
    public void draw() {
        flyweight.draw(x, y);
    }

    /**
     * Moves the particle by a delta (only extrinsic state changes — flyweight is untouched).
     *
     * @param dx horizontal delta
     * @param dy vertical delta
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public double getX()                        { return x; }
    public double getY()                        { return y; }
    public ParticleFlyweight getFlyweight()     { return flyweight; }
}
