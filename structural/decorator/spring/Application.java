package structural.decorator.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import structural.decorator.after.Order;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Run the Spring Boot application demo
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(OrderPricingService pricingService) {
        return args -> {
            System.out.println("=== Starting Decorator Pattern Spring Demo ===\n");

            // Case 1: Simple Order
            Order order1 = pricingService.calculateOrderPrice(100.0, "Laptop Stand", false, false, null);
            printOrderDetails("Case 1: Standard Order", order1);

            // Case 2: VIP Customer Order
            Order order2 = pricingService.calculateOrderPrice(100.0, "Laptop Stand", true, false, null);
            printOrderDetails("Case 2: VIP Customer Order", order2);

            // Case 3: VIP Customer Order on Black Friday with Promo Code SAVE20
            Order order3 = pricingService.calculateOrderPrice(100.0, "Laptop Stand", true, true, "SAVE20");
            printOrderDetails("Case 3: Stacked Discounts (VIP + Black Friday + Promo SAVE20)", order3);

            // Case 4: Standard Order with SAVE10
            Order order4 = pricingService.calculateOrderPrice(50.0, "Wireless Mouse", false, false, "SAVE10");
            printOrderDetails("Case 4: Standard Order with Promo SAVE10", order4);
        };
    }

    private void printOrderDetails(String title, Order order) {
        System.out.println("--- " + title + " ---");
        System.out.printf("Description: %s%n", order.getDescription());
        System.out.printf("Final Price: $%.2f%n%n", order.getPrice());
    }
}
