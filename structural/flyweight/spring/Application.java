package structural.flyweight.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Spring Boot entry point for the Flyweight Pattern demo.
 *
 * <p>How Spring wires the Flyweight pattern:
 * <ol>
 *   <li>{@link ProductFlyweightFactory} is a {@code @Component} singleton bean — the cache
 *       lives for the lifetime of the application context.</li>
 *   <li>{@link CatalogueService} is a {@code @Service} bean that receives the factory via
 *       constructor injection.</li>
 *   <li>The {@link CommandLineRunner} bean acts as the client, triggering the demo on
 *       application startup.</li>
 * </ol>
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * CommandLineRunner bean — the "client" in the Flyweight pattern.
     * Spring injects {@code CatalogueService} automatically (Bean Management).
     */
    @Bean
    public CommandLineRunner run(CatalogueService catalogueService) {
        return args -> {
            System.out.println("=== Flyweight Pattern — Spring Boot Demo ===\n");
            System.out.println("Building search result page...\n");

            List<ProductListingContext> results = catalogueService.buildSearchResults();
            catalogueService.renderCatalogue(results);

            System.out.println("\n✅ Demo complete.");
        };
    }
}
