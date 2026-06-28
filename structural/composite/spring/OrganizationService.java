package structural.composite.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import structural.composite.after.OrganizationComponent;

@Service
public class OrganizationService {

    private final OrganizationComponent company;

    // Injecting the root component of our organization tree
    @Autowired
    public OrganizationService(@Qualifier("company") OrganizationComponent company) {
        this.company = company;
    }

    public void printCompanyStructure() {
        System.out.println("=== Company Structure ===");
        company.printStructure(0);
    }

    public double calculateTotalCompanySalary() {
        return company.calculateSalary();
    }
}
