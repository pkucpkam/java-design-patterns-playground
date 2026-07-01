package structural.proxy.before;

import java.util.HashMap;
import java.util.Map;

/**
 * Problematic VideoService that handles streaming logic directly.
 *
 * <p>This class mixes several responsibilities together:
 * <ul>
 *     <li>Fetching & loading video data (could be expensive / remote call)</li>
 *     <li>Authorization check (hardcoded inside the service)</li>
 *     <li>Caching (raw Map field, no separation)</li>
 *     <li>Logging / audit trail (scattered System.out calls)</li>
 * </ul>
 *
 * <p>Violated SOLID Principles:
 * <ul>
 *     <li>Single Responsibility Principle (SRP): One class manages loading,
 *         caching, access-control AND logging simultaneously.</li>
 *     <li>Open/Closed Principle (OCP): Adding a new cross-cutting concern
 *         (e.g., rate-limiting) requires modifying this file directly.</li>
 *     <li>Dependency Inversion Principle (DIP): High-level callers depend on
 *         this concrete class; there is no abstraction to program against.</li>
 * </ul>
 */
public class VideoService {

    // ---- Inline cache — no separation of concerns ----
    private final Map<String, String> cache = new HashMap<>();

    /**
     * Loads a video for the given user.
     * Every concern is crammed into this one method.
     *
     * @param videoId the identifier of the video to stream
     * @param userId  the identifier of the requesting user
     * @return video content string, or an error/denial message
     */
    public String loadVideo(String videoId, String userId) {

        // ---- 1. Authorization (hardcoded rule) ----
        if (!isAuthorized(userId)) {
            System.out.println("[AUTH] Access denied for user: " + userId);
            return "ACCESS DENIED";
        }

        // ---- 2. Cache look-up (mixed with business logic) ----
        if (cache.containsKey(videoId)) {
            System.out.println("[CACHE] Returning cached video: " + videoId);
            return cache.get(videoId);
        }

        // ---- 3. Actual (expensive) load ----
        System.out.println("[DB] Loading video from remote storage: " + videoId);
        String content = fetchFromRemote(videoId);

        // ---- 4. Store in cache ----
        cache.put(videoId, content);

        // ---- 5. Audit log ----
        System.out.println("[AUDIT] User " + userId + " accessed video " + videoId);

        return content;
    }

    // ---- Private helpers — all in the same class ----

    private boolean isAuthorized(String userId) {
        // Hardcoded list — impossible to extend without touching this file
        return "premium_user".equals(userId) || "admin".equals(userId);
    }

    private String fetchFromRemote(String videoId) {
        // Simulates a slow / expensive I/O call
        return "Video content of [" + videoId + "]";
    }
}
