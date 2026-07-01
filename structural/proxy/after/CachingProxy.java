package structural.proxy.after;

import java.util.HashMap;
import java.util.Map;

/**
 * Caching Proxy — adds a transparent in-memory cache in front of the
 * wrapped {@link VideoService}.
 *
 * <p>On the first request for a video, the result is fetched from the
 * delegate and stored in the cache.  Subsequent requests for the same video
 * are served directly from the cache, avoiding expensive downstream calls.
 *
 * <p>Design Principles satisfied:
 * <ul>
 *     <li>Single Responsibility Principle (SRP): owns <em>only</em>
 *         caching logic; nothing else.</li>
 *     <li>Open/Closed Principle (OCP): caching strategy can be swapped
 *         (e.g., Redis, Caffeine) by providing a different implementation
 *         without changing this proxy or the real service.</li>
 * </ul>
 *
 * <p>Real-world analogy: a Content Delivery Network (CDN) node that
 * serves cached copies of videos closer to the viewer, only pulling from
 * the origin server when the cache is cold.
 */
public class CachingProxy implements VideoService {

    private final VideoService delegate;
    private final Map<String, String> cache = new HashMap<>();

    /**
     * Constructs the caching proxy.
     *
     * @param delegate the next {@link VideoService} in the chain
     * @throws IllegalArgumentException if {@code delegate} is {@code null}
     */
    public CachingProxy(VideoService delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate VideoService must not be null.");
        }
        this.delegate = delegate;
    }

    /**
     * Serves the video from cache when available; otherwise fetches it
     * from the delegate and stores the result for future calls.
     *
     * @param videoId the video identifier used as cache key
     * @param userId  the requesting user (forwarded to the delegate when needed)
     * @return video content string (from cache or delegate)
     */
    @Override
    public String loadVideo(String videoId, String userId) {
        if (cache.containsKey(videoId)) {
            System.out.println("[CachingProxy] Cache HIT for video: " + videoId);
            return cache.get(videoId);
        }

        System.out.println("[CachingProxy] Cache MISS for video: " + videoId + " — forwarding to delegate.");
        String content = delegate.loadVideo(videoId, userId);
        cache.put(videoId, content);
        return content;
    }

    /**
     * Returns the current number of cached entries (useful for testing).
     *
     * @return number of cached videos
     */
    public int getCacheSize() {
        return cache.size();
    }
}
