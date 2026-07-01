package structural.proxy.after;

/**
 * Logging Proxy — records every call to the wrapped {@link VideoService}
 * including the caller's identity, the requested video, and the outcome.
 *
 * <p>This proxy satisfies the audit/observability requirement that would
 * otherwise pollute the real service with unrelated {@code System.out} calls.
 *
 * <p>Design Principles satisfied:
 * <ul>
 *     <li>Single Responsibility Principle (SRP): responsible only for
 *         audit logging.</li>
 *     <li>Open/Closed Principle (OCP): a different logging backend (SLF4J,
 *         Elasticsearch) can be plugged in without touching other classes.</li>
 * </ul>
 *
 * <p>Real-world analogy: an API gateway that logs every inbound request and
 * outbound response for compliance and debugging purposes.
 */
public class LoggingProxy implements VideoService {

    private final VideoService delegate;

    /**
     * Constructs the logging proxy.
     *
     * @param delegate the next {@link VideoService} in the chain
     * @throws IllegalArgumentException if {@code delegate} is {@code null}
     */
    public LoggingProxy(VideoService delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate VideoService must not be null.");
        }
        this.delegate = delegate;
    }

    /**
     * Logs the request, delegates to the next service, then logs the result.
     *
     * @param videoId the requested video
     * @param userId  the requesting user
     * @return video content string
     */
    @Override
    public String loadVideo(String videoId, String userId) {
        System.out.printf("[LoggingProxy] REQUEST  — user=%s, video=%s%n", userId, videoId);

        String result = delegate.loadVideo(videoId, userId);

        System.out.printf("[LoggingProxy] RESPONSE — user=%s, video=%s, contentLength=%d chars%n",
                userId, videoId, result.length());

        return result;
    }
}
