package structural.composite.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Only run this if you want to test the Spring Boot context locally.
        // In a real application, this would be your main entry point.
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(OrganizationService organizationService) {
        return args -> {
            System.out.println("Starting Composite Pattern Spring Demo...\n");
            
            organizationService.printCompanyStructure();
            
            double totalSalary = organizationService.calculateTotalCompanySalary();
            System.out.println("\nTotal Company Salary: $" + totalSalary);
        };
    }
}
