package structural.flyweight.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Client (Game) — demonstrates the Flyweight pattern in action.
 *
 * <p>Key difference from the "before" version:
 * <ul>
 *   <li>The game no longer creates unique {@code Particle} objects that each contain a full
 *       copy of color and sprite.</li>
 *   <li>Instead, it asks the {@link ParticleFlyweightFactory} for a shared flyweight, then
 *       wraps it in a lightweight {@link ParticleContext} that stores only the position.</li>
 *   <li>No matter how many bullets are spawned, only <b>one</b> {@code ConcreteParticleFlyweight}
 *       with type "BULLET" exists in the JVM heap.</li>
 * </ul>
 */
public class Game {

    private final List<ParticleContext> activeParticles = new ArrayList<>();
    private final ParticleFlyweightFactory factory = new ParticleFlyweightFactory();
    private final Random random = new Random(42);

    // ── Spawn helpers ─────────────────────────────────────────────────────────

    /**
     * Spawns {@code count} bullet particles at random screen positions.
     * Only ONE "BULLET" flyweight is ever created regardless of count.
     */
    public void spawnBullets(int count) {
        for (int i = 0; i < count; i++) {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");
            activeParticles.add(new ParticleContext(fw, random.nextDouble() * 1920,
                                                        random.nextDouble() * 1080));
        }
    }

    /**
     * Spawns {@code count} missile particles.
     * Only ONE "MISSILE" flyweight is ever created regardless of count.
     */
    public void spawnMissiles(int count) {
        for (int i = 0; i < count; i++) {
            ParticleFlyweight fw = factory.getFlyweight("MISSILE", "BLUE", "missile.png");
            activeParticles.add(new ParticleContext(fw, random.nextDouble() * 1920,
                                                        random.nextDouble() * 1080));
        }
    }

    /**
     * Spawns {@code count} shrapnel particles.
     * Only ONE "SHRAPNEL" flyweight is created.
     */
    public void spawnShrapnel(int count) {
        for (int i = 0; i < count; i++) {
            ParticleFlyweight fw = factory.getFlyweight("SHRAPNEL", "ORANGE", "shrapnel.png");
            activeParticles.add(new ParticleContext(fw, random.nextDouble() * 1920,
                                                        random.nextDouble() * 1080));
        }
    }

    /** Renders all active particles (delegates to flyweight + passes extrinsic coords). */
    public void drawAll() {
        activeParticles.forEach(ParticleContext::draw);
    }

    // ── Statistics ────────────────────────────────────────────────────────────

    public int activeParticleCount()   { return activeParticles.size(); }
    public int cachedFlyweightCount()  { return factory.cachedCount();  }
    public ParticleFlyweightFactory getFactory() { return factory; }

    // ── Demo entry point ──────────────────────────────────────────────────────

    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("=== Game WITH Flyweight Pattern ===\n");
        System.out.println("--- Spawning particles (watch factory cache hits/misses) ---");

        game.spawnBullets(5);   // factory creates 1 BULLET flyweight
        game.spawnMissiles(3);  // factory creates 1 MISSILE flyweight
        game.spawnShrapnel(4);  // factory creates 1 SHRAPNEL flyweight
        game.spawnBullets(3);   // factory REUSES existing BULLET flyweight (cache HIT)

        System.out.println("\n--- Drawing all particles ---");
        game.drawAll();

        System.out.println("\n=== Summary ===");
        System.out.printf("Active particle contexts : %d%n", game.activeParticleCount());
        System.out.printf("Flyweight objects in JVM : %d  ← shared!%n", game.cachedFlyweightCount());
        System.out.println("Memory savings: " + game.activeParticleCount() + " contexts share only "
                + game.cachedFlyweightCount() + " flyweight object(s).");
    }
}
