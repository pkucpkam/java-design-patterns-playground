package structural.composite.before;

public class Main {
    public static void main(String[] args) {
        // 1. Create Employees
        Employee dev1 = new Employee("Alice", "Backend Developer", 3000);
        Employee dev2 = new Employee("Bob", "Frontend Developer", 2800);
        Employee lead = new Employee("Charlie", "Tech Lead", 4500);
        
        Employee hr1 = new Employee("Diana", "HR Specialist", 2500);

        // 2. Create Departments
        Department engineering = new Department("Engineering");
        engineering.addEmployee(dev1);
        engineering.addEmployee(dev2);
        engineering.addEmployee(lead);

        Department hr = new Department("Human Resources");
        hr.addEmployee(hr1);

        Department headOffice = new Department("Head Office");
        headOffice.addSubDepartment(engineering);
        headOffice.addSubDepartment(hr);

        // 3. Calculate Salaries
        SalaryCalculator calculator = new SalaryCalculator();
        
        // BAD: Client code has to treat Department and Employee differently.
        System.out.println("Total Head Office Salary: $" + calculator.calculateTotalSalary(headOffice));
        System.out.println("Engineering Dept Salary: $" + calculator.calculateTotalSalary(engineering));
        System.out.println("Alice's Salary: $" + calculator.calculateEmployeeSalary(dev1)); // Different method required
        
        // BAD: Printing the structure requires complex conditional logic.
    }
}
