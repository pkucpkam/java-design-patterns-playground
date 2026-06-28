package structural.composite.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import structural.composite.after.Department;
import structural.composite.after.Employee;
import structural.composite.after.OrganizationComponent;

@Configuration
public class OrganizationConfig {

    @Bean
    public OrganizationComponent ceo() {
        return new Employee("Steve", "CEO", 10000);
    }

    @Bean
    public OrganizationComponent headOfEngineering() {
        return new Employee("Alice", "VP of Engineering", 8000);
    }

    @Bean
    public OrganizationComponent developer() {
        return new Employee("Bob", "Senior Developer", 5000);
    }

    @Bean
    public OrganizationComponent engineeringDepartment() {
        OrganizationComponent engineering = new Department("Engineering");
        engineering.add(headOfEngineering());
        engineering.add(developer());
        return engineering;
    }

    @Bean
    public OrganizationComponent company() {
        OrganizationComponent company = new Department("Company Head Office");
        company.add(ceo());
        company.add(engineeringDepartment());
        return company;
    }
}
