package structural.proxy.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring Boot entry point for the Proxy Pattern demo.
 *
 * <p>Key annotations:
 * <ul>
 *     <li>{@code @EnableCaching} — activates Spring's Caching Proxy
 *         ({@code @Cacheable} on {@link RealVideoService#loadVideo}).</li>
 *     <li>{@code @EnableAspectJAutoProxy} — activates the AOP infrastructure
 *         that generates the authorization + logging proxy via
 *         {@link VideoServiceAspect}.</li>
 * </ul>
 *
 * <p>At runtime Spring wraps the {@link RealVideoService} bean inside
 * <strong>two dynamically generated proxy objects</strong> — exactly the same
 * layering as the hand-crafted pure-Java solution, but produced automatically
 * by the framework's Bean Management and Dependency Injection machinery.
 */
@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Demo runner that exercises the proxy chain.
     *
     * <p>Spring injects the {@link RealVideoService} bean here — but what it
     * actually injects is a CGLIB proxy that transparently incorporates the
     * AOP advice and caching behaviour defined elsewhere.
     *
     * @param videoService the proxy-wrapped video service bean
     * @return a {@link CommandLineRunner} that prints demo results to stdout
     */
    @Bean
    public CommandLineRunner run(RealVideoService videoService) {
        return args -> {
            System.out.println("=== Proxy Pattern — Spring Boot Demo ===\n");

            // Case 1: First load — cache MISS, real service is called
            System.out.println("--- Case 1: First load of video-101 (premium_user) ---");
            String content1 = videoService.loadVideo("video-101");
            System.out.println("Result: " + content1 + "\n");

            // Case 2: Second load of same video — cache HIT, real service NOT called
            System.out.println("--- Case 2: Second load of video-101 (cache hit expected) ---");
            String content2 = videoService.loadVideo("video-101");
            System.out.println("Result: " + content2 + "\n");

            // Case 3: Different video — cache MISS again
            System.out.println("--- Case 3: First load of video-202 ---");
            String content3 = videoService.loadVideo("video-202");
            System.out.println("Result: " + content3 + "\n");

            System.out.println("=== Demo Complete ===");
        };
    }
}
