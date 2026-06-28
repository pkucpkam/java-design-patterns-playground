package structural.composite.after;

public class Main {
    public static void main(String[] args) {
        // 1. Create Leaf elements (Employees)
        OrganizationComponent dev1 = new Employee("Alice", "Backend Developer", 3000);
        OrganizationComponent dev2 = new Employee("Bob", "Frontend Developer", 2800);
        OrganizationComponent lead = new Employee("Charlie", "Tech Lead", 4500);
        
        OrganizationComponent hr1 = new Employee("Diana", "HR Specialist", 2500);

        // 2. Create Composite elements (Departments)
        OrganizationComponent engineering = new Department("Engineering");
        engineering.add(dev1);
        engineering.add(dev2);
        engineering.add(lead);

        OrganizationComponent hr = new Department("Human Resources");
        hr.add(hr1);

        OrganizationComponent headOffice = new Department("Head Office");
        headOffice.add(engineering);
        headOffice.add(hr);

        // 3. Client code treats Leaf and Composite uniformly!
        // No need to check if it's an Employee or Department.
        
        System.out.println("--- Structure ---");
        headOffice.printStructure(0);
        
        System.out.println("\n--- Salaries ---");
        System.out.println("Total Head Office Salary: $" + headOffice.calculateSalary());
        System.out.println("Engineering Dept Salary: $" + engineering.calculateSalary());
        System.out.println("Alice's Salary: $" + dev1.calculateSalary());
    }
}
