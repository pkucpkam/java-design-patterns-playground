package structural.proxy.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import structural.proxy.after.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Proxy Pattern implementation — Video Streaming Service.
 *
 * <p>Test coverage:
 * <ul>
 *     <li>Happy Path  — authorized users get video content (cached & uncached)</li>
 *     <li>Edge Cases  — cache hit on second call, empty userId/videoId</li>
 *     <li>Failure Cases — unauthorized user throws {@link AccessDeniedException},
 *         null delegate throws {@link IllegalArgumentException}</li>
 * </ul>
 */
class ProxyPatternTest {

    private static final String PREMIUM_USER = "premium_user";
    private static final String GUEST_USER   = "guest_user";
    private static final String VIDEO_ID     = "video-101";

    private RealVideoService    realService;
    private Set<String>         authorizedUsers;

    @BeforeEach
    void setUp() {
        realService     = new RealVideoService();
        authorizedUsers = Set.of(PREMIUM_USER, "admin");
    }

    // =========================================================================
    // Happy Path Tests
    // =========================================================================

    @Nested
    @DisplayName("Happy Path")
    class HappyPath {

        @Test
        @DisplayName("Authorized user loads video through full proxy chain")
        void authorizedUser_loadsVideo_successfully() {
            // Arrange — chain: LoggingProxy → AuthorizationProxy → CachingProxy → RealVideoService
            VideoService service = new LoggingProxy(
                new AuthorizationProxy(
                    new CachingProxy(realService),
                    authorizedUsers
                )
            );

            // Act
            String content = service.loadVideo(VIDEO_ID, PREMIUM_USER);

            // Assert
            assertNotNull(content);
            assertTrue(content.contains(VIDEO_ID),
                "Returned content should reference the requested video ID");
        }

        @Test
        @DisplayName("RealVideoService returns expected content directly")
        void realService_returnsContent_forAnyUser() {
            String content = realService.loadVideo(VIDEO_ID, GUEST_USER);
            assertEquals("Video content of [" + VIDEO_ID + "]", content);
        }

        @Test
        @DisplayName("Logging proxy forwards correct content from delegate")
        void loggingProxy_forwardsContent_fromDelegate() {
            VideoService service = new LoggingProxy(realService);
            String content = service.loadVideo(VIDEO_ID, PREMIUM_USER);
            assertEquals("Video content of [" + VIDEO_ID + "]", content);
        }
    }

    // =========================================================================
    // Edge Case Tests
    // =========================================================================

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("Cache returns same content on second call (cache hit)")
        void cachingProxy_cacheHit_onSecondCall() {
            CachingProxy cachingProxy = new CachingProxy(realService);

            String firstCall  = cachingProxy.loadVideo(VIDEO_ID, PREMIUM_USER);
            String secondCall = cachingProxy.loadVideo(VIDEO_ID, PREMIUM_USER);

            assertEquals(firstCall, secondCall, "Both calls must return identical content");
            assertEquals(1, cachingProxy.getCacheSize(), "Only one entry should be cached");
        }

        @Test
        @DisplayName("Cache stores different videos independently")
        void cachingProxy_storesTwoVideos_independently() {
            CachingProxy cachingProxy = new CachingProxy(realService);

            String video1 = cachingProxy.loadVideo("video-001", PREMIUM_USER);
            String video2 = cachingProxy.loadVideo("video-002", PREMIUM_USER);

            assertNotEquals(video1, video2, "Different videos should return different content");
            assertEquals(2, cachingProxy.getCacheSize());
        }

        @Test
        @DisplayName("AuthorizationProxy allows admin user to load video")
        void authorizationProxy_allowsAdmin_toLoadVideo() {
            VideoService service = new AuthorizationProxy(realService, authorizedUsers);
            assertDoesNotThrow(() -> service.loadVideo(VIDEO_ID, "admin"));
        }
    }

    // =========================================================================
    // Failure Case Tests
    // =========================================================================

    @Nested
    @DisplayName("Failure Cases")
    class FailureCases {

        @Test
        @DisplayName("Unauthorized user throws AccessDeniedException")
        void authorizationProxy_throwsAccessDeniedException_forUnauthorizedUser() {
            VideoService service = new AuthorizationProxy(realService, authorizedUsers);

            AccessDeniedException ex = assertThrows(
                AccessDeniedException.class,
                () -> service.loadVideo(VIDEO_ID, GUEST_USER)
            );

            assertTrue(ex.getMessage().contains(GUEST_USER),
                "Exception message should identify the blocked user");
            assertTrue(ex.getMessage().contains(VIDEO_ID),
                "Exception message should identify the requested video");
        }

        @Test
        @DisplayName("AuthorizationProxy constructor rejects null delegate")
        void authorizationProxy_throwsIllegalArgumentException_whenDelegateIsNull() {
            assertThrows(IllegalArgumentException.class,
                () -> new AuthorizationProxy(null, authorizedUsers));
        }

        @Test
        @DisplayName("CachingProxy constructor rejects null delegate")
        void cachingProxy_throwsIllegalArgumentException_whenDelegateIsNull() {
            assertThrows(IllegalArgumentException.class,
                () -> new CachingProxy(null));
        }

        @Test
        @DisplayName("LoggingProxy constructor rejects null delegate")
        void loggingProxy_throwsIllegalArgumentException_whenDelegateIsNull() {
            assertThrows(IllegalArgumentException.class,
                () -> new LoggingProxy(null));
        }

        @Test
        @DisplayName("AccessDeniedException propagates through LoggingProxy")
        void accessDeniedException_propagatesThroughLoggingProxy() {
            // LoggingProxy wraps AuthorizationProxy — exception must bubble up
            VideoService service = new LoggingProxy(
                new AuthorizationProxy(realService, authorizedUsers)
            );

            assertThrows(AccessDeniedException.class,
                () -> service.loadVideo(VIDEO_ID, GUEST_USER));
        }
    }
}
