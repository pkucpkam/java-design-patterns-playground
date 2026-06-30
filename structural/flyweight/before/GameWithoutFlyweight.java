package structural.flyweight.before;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game client that spawns millions of particles WITHOUT the Flyweight pattern.
 *
 * <p>Problem: Every {@link Particle} created here stores its own copy of {@code color} and
 * {@code sprite}.  Imagine the sprite field as a 2 MB image — with 1 000 000 "RED" bullets
 * every instance wastes another 2 MB on the exact same data.
 *
 * <p>Run this and compare heap usage with the "after" version.
 */
public class GameWithoutFlyweight {

    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random(42); // fixed seed for reproducibility

    public void spawnBullets(int count) {
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * 1920;
            double y = random.nextDouble() * 1080;
            // Each new Particle holds its OWN copy of "RED" + "bullet.png"
            particles.add(new Particle("RED", "bullet.png", x, y));
        }
    }

    public void spawnMissiles(int count) {
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * 1920;
            double y = random.nextDouble() * 1080;
            particles.add(new Particle("BLUE", "missile.png", x, y));
        }
    }

    public void drawAll() {
        particles.forEach(Particle::draw);
    }

    public int particleCount() {
        return particles.size();
    }

    public static void main(String[] args) {
        GameWithoutFlyweight game = new GameWithoutFlyweight();

        System.out.println("=== Game WITHOUT Flyweight Pattern ===");
        System.out.println("Spawning 5 bullets and 3 missiles for demo...\n");

        game.spawnBullets(5);
        game.spawnMissiles(3);
        game.drawAll();

        System.out.printf("%nTotal particle objects created: %d%n", game.particleCount());
        System.out.println("Each object duplicates color + sprite data → high memory usage!");
    }
}
