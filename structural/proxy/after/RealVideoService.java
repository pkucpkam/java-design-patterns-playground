package structural.proxy.after;

/**
 * Real Subject — the genuine, expensive implementation.
 *
 * <p>This class has exactly one job: fetch video content from the remote
 * storage layer.  It knows nothing about caching, authorization, or logging;
 * those cross-cutting concerns are handled by the proxy chain that wraps it.
 *
 * <p>Single Responsibility Principle (SRP): responsible solely for
 * retrieving video data.
 */
public class RealVideoService implements VideoService {

    /**
     * Simulates a slow, expensive remote call (e.g., database or CDN fetch).
     *
     * @param videoId the unique identifier of the video
     * @param userId  the requesting user (not used here — auth is the proxy's job)
     * @return the raw video content string
     */
    @Override
    public String loadVideo(String videoId, String userId) {
        System.out.println("[RealVideoService] Fetching video from remote storage: " + videoId);
        // Simulate I/O latency
        return "Video content of [" + videoId + "]";
    }
}
