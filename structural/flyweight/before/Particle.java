package structural.flyweight.before;

/**
 * Problematic Particle class in a game rendering system.
 *
 * <p>This "god object" stores ALL data for a single particle:
 * <ul>
 *   <li><b>Shared (intrinsic) state</b>: color, sprite — identical for all bullets of the
 *       same type, yet duplicated inside every instance.</li>
 *   <li><b>Unique (extrinsic) state</b>: x, y coordinates — genuinely different per object.</li>
 * </ul>
 *
 * <p>Violated Principles:
 * <ul>
 *   <li><b>Single Responsibility Principle (SRP)</b>: One class is responsible for both the
 *       shared appearance data AND the per-instance positional data.</li>
 *   <li><b>Memory efficiency</b>: Spawning 1,000,000 bullet particles with the same color/sprite
 *       wastes memory because each instance stores a full copy of that redundant data.</li>
 * </ul>
 */
public class Particle {

    // ── Intrinsic state (shared, but duplicated in every instance) ────────────
    private final String color;   // e.g. "RED", "BLUE"
    private final String sprite;  // e.g. "bullet.png" – could be megabytes in reality

    // ── Extrinsic state (unique per particle) ─────────────────────────────────
    private double x;
    private double y;

    public Particle(String color, String sprite, double x, double y) {
        this.color  = color;
        this.sprite = sprite;
        this.x      = x;
        this.y      = y;
    }

    public void draw() {
        System.out.printf("Drawing particle [color=%s, sprite=%s] at (%.1f, %.1f)%n",
                color, sprite, x, y);
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getColor()  { return color;  }
    public String getSprite() { return sprite; }
    public double getX()      { return x;      }
    public double getY()      { return y;      }
}
