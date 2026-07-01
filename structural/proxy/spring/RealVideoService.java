package structural.proxy.spring;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Real Subject — Spring-managed bean that performs the actual (expensive)
 * video loading operation.
 *
 * <p>This class has no knowledge of caching, authorization, or logging.
 * Spring's AOP-based proxy infrastructure will weave those cross-cutting
 * concerns around this bean at runtime, exactly mirroring the pure-Java
 * Proxy Pattern but using Spring's built-in mechanism.
 */
@Service
public class RealVideoService {

    /**
     * Fetches the video content from the (simulated) remote storage.
     *
     * <p>Spring's {@code @Cacheable} annotation instructs the framework to
     * generate a <strong>Caching Proxy</strong> (backed by the configured
     * {@code CacheManager}) around this bean.  On the first call the real
     * method executes; on subsequent calls with the same {@code videoId} the
     * proxy returns the cached result without invoking this method.
     *
     * @param videoId the unique identifier of the requested video
     * @return the raw video content string
     */
    @Cacheable(value = "videos", key = "#videoId")
    public String loadVideo(String videoId) {
        System.out.println("[RealVideoService] Fetching from remote storage: " + videoId);
        // Simulates slow I/O
        return "Video content of [" + videoId + "]";
    }
}
