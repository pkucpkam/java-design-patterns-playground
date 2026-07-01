package structural.proxy.after;

/**
 * Subject interface — the common contract that both the real service
 * and every proxy must honour.
 *
 * <p>This is the cornerstone of the Proxy Pattern: by programming against
 * this interface, callers never need to know whether they are talking to the
 * real object or to a proxy sitting in front of it.
 *
 * <p>Real-world analogy: a streaming platform contract that guarantees
 * every implementation can load a video on behalf of a user.
 */
public interface VideoService {

    /**
     * Loads and returns the content of a video.
     *
     * @param videoId the unique identifier of the video to stream
     * @param userId  the identifier of the requesting user
     * @return video content string
     */
    String loadVideo(String videoId, String userId);
}
