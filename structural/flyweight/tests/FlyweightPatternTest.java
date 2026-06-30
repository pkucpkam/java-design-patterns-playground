package structural.flyweight.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import structural.flyweight.after.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Flyweight Pattern implementation.
 *
 * <p>Test coverage:
 * <ul>
 *   <li><b>Happy Path</b>: normal creation, caching, drawing, moving.</li>
 *   <li><b>Edge Cases</b>: cache hit with same type, multiple types, zero-coordinate particles.</li>
 *   <li><b>Failure Cases</b>: null/blank arguments, negative price, null flyweight.</li>
 * </ul>
 */
@DisplayName("Flyweight Pattern Tests")
class FlyweightPatternTest {

    private ParticleFlyweightFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ParticleFlyweightFactory();
    }

    // =========================================================================
    // Happy Path Tests
    // =========================================================================

    @Nested
    @DisplayName("Happy Path")
    class HappyPath {

        @Test
        @DisplayName("Factory creates a flyweight on first request (cache miss)")
        void testFactoryCreatesFlyweightOnCacheMiss() {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");

            assertNotNull(fw, "Flyweight must not be null");
            assertEquals("BULLET", fw.getType());
            assertEquals(1, factory.cachedCount(), "One type → one cached flyweight");
        }

        @Test
        @DisplayName("Factory returns SAME instance for identical type (cache hit)")
        void testFactoryReturnsSameInstanceForSameType() {
            ParticleFlyweight fw1 = factory.getFlyweight("BULLET", "RED", "bullet.png");
            ParticleFlyweight fw2 = factory.getFlyweight("BULLET", "RED", "bullet.png");

            assertSame(fw1, fw2,
                    "Same type must return identical (==) shared flyweight instance");
            assertEquals(1, factory.cachedCount(),
                    "Two requests for same type → still only one cached object");
        }

        @Test
        @DisplayName("Factory stores different flyweights for different types")
        void testFactoryStoresDifferentFlyweightsForDifferentTypes() {
            ParticleFlyweight bullet  = factory.getFlyweight("BULLET",   "RED",    "bullet.png");
            ParticleFlyweight missile = factory.getFlyweight("MISSILE",  "BLUE",   "missile.png");
            ParticleFlyweight shrapnel= factory.getFlyweight("SHRAPNEL", "ORANGE", "shrapnel.png");

            assertNotSame(bullet, missile);
            assertNotSame(bullet, shrapnel);
            assertNotSame(missile, shrapnel);
            assertEquals(3, factory.cachedCount());
        }

        @Test
        @DisplayName("ParticleContext.draw() delegates to the flyweight without error")
        void testParticleContextDrawDelegatesToFlyweight() {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");
            ParticleContext ctx  = new ParticleContext(fw, 100.0, 200.0);

            // Should not throw; visual output goes to stdout
            assertDoesNotThrow(ctx::draw);
        }

        @Test
        @DisplayName("ParticleContext.move() updates only extrinsic coordinates")
        void testParticleContextMoveUpdatesCoordinates() {
            ParticleFlyweight fw  = factory.getFlyweight("BULLET", "RED", "bullet.png");
            ParticleContext ctx   = new ParticleContext(fw, 10.0, 20.0);

            ctx.move(5.0, -3.0);

            assertEquals(15.0, ctx.getX(), 0.001);
            assertEquals(17.0, ctx.getY(), 0.001);
        }

        @Test
        @DisplayName("Game spawns many particles but reuses a single flyweight per type")
        void testGameFlyweightReuseAcrossSpawns() {
            Game game = new Game();
            game.spawnBullets(1000);
            game.spawnMissiles(500);

            assertEquals(1500, game.activeParticleCount(),
                    "1 000 bullets + 500 missiles → 1 500 contexts");
            assertEquals(2, game.cachedFlyweightCount(),
                    "Only 2 flyweight objects in memory (BULLET + MISSILE)");
        }

        @Test
        @DisplayName("getCachedFlyweights returns unmodifiable view of all cached types")
        void testGetCachedFlyweightsReturnsCorrectMap() {
            factory.getFlyweight("BULLET",  "RED",  "bullet.png");
            factory.getFlyweight("MISSILE", "BLUE", "missile.png");

            Map<String, ParticleFlyweight> cached = factory.getCachedFlyweights();

            assertEquals(2, cached.size());
            assertTrue(cached.containsKey("BULLET"));
            assertTrue(cached.containsKey("MISSILE"));

            // Must be unmodifiable
            assertThrows(UnsupportedOperationException.class,
                    () -> cached.put("BOMB", null));
        }
    }

    // =========================================================================
    // Edge Case Tests
    // =========================================================================

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("Particle at origin (0, 0) is valid")
        void testParticleAtOrigin() {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");
            ParticleContext ctx  = new ParticleContext(fw, 0.0, 0.0);

            assertEquals(0.0, ctx.getX(), 0.001);
            assertEquals(0.0, ctx.getY(), 0.001);
            assertDoesNotThrow(ctx::draw);
        }

        @Test
        @DisplayName("Multiple move() calls accumulate correctly")
        void testMultipleMoveCallsAccumulate() {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");
            ParticleContext ctx  = new ParticleContext(fw, 0.0, 0.0);

            ctx.move(10.0, 0.0);
            ctx.move(10.0, 0.0);
            ctx.move(10.0, 0.0);

            assertEquals(30.0, ctx.getX(), 0.001);
        }

        @Test
        @DisplayName("Negative coordinates (moving off-screen) are accepted")
        void testNegativeCoordinatesAccepted() {
            ParticleFlyweight fw = factory.getFlyweight("MISSILE", "BLUE", "missile.png");
            ParticleContext ctx  = new ParticleContext(fw, 0.0, 0.0);

            ctx.move(-100.0, -200.0);

            assertEquals(-100.0, ctx.getX(), 0.001);
            assertEquals(-200.0, ctx.getY(), 0.001);
        }

        @Test
        @DisplayName("Factory cache is empty at construction time")
        void testFactoryStartsEmpty() {
            ParticleFlyweightFactory freshFactory = new ParticleFlyweightFactory();
            assertEquals(0, freshFactory.cachedCount());
            assertTrue(freshFactory.getCachedFlyweights().isEmpty());
        }

        @Test
        @DisplayName("Same flyweight is referenced by multiple contexts (shared reference)")
        void testSameFlyweightReferencedByMultipleContexts() {
            ParticleFlyweight fw = factory.getFlyweight("BULLET", "RED", "bullet.png");

            ParticleContext ctx1 = new ParticleContext(fw, 10.0, 20.0);
            ParticleContext ctx2 = new ParticleContext(fw, 50.0, 60.0);
            ParticleContext ctx3 = new ParticleContext(fw, 90.0, 10.0);

            // All three contexts share the SAME flyweight object
            assertSame(ctx1.getFlyweight(), ctx2.getFlyweight());
            assertSame(ctx2.getFlyweight(), ctx3.getFlyweight());

            // But their coordinates are independent
            assertNotEquals(ctx1.getX(), ctx2.getX());
        }
    }

    // =========================================================================
    // Failure Case Tests
    // =========================================================================

    @Nested
    @DisplayName("Failure Cases")
    class FailureCases {

        @Test
        @DisplayName("ConcreteParticleFlyweight rejects null type")
        void testFlyweightRejectsNullType() {
            assertThrows(IllegalArgumentException.class,
                    () -> factory.getFlyweight(null, "RED", "bullet.png"));
        }

        @Test
        @DisplayName("ConcreteParticleFlyweight rejects blank type")
        void testFlyweightRejectsBlankType() {
            assertThrows(IllegalArgumentException.class,
                    () -> factory.getFlyweight("  ", "RED", "bullet.png"));
        }

        @Test
        @DisplayName("ConcreteParticleFlyweight rejects null color")
        void testFlyweightRejectsNullColor() {
            assertThrows(IllegalArgumentException.class,
                    () -> factory.getFlyweight("BULLET", null, "bullet.png"));
        }

        @Test
        @DisplayName("ConcreteParticleFlyweight rejects blank sprite")
        void testFlyweightRejectsBlankSprite() {
            assertThrows(IllegalArgumentException.class,
                    () -> factory.getFlyweight("BULLET", "RED", ""));
        }

        @Test
        @DisplayName("ParticleContext rejects null flyweight")
        void testContextRejectsNullFlyweight() {
            assertThrows(IllegalArgumentException.class,
                    () -> new ParticleContext(null, 0.0, 0.0));
        }
    }
}
