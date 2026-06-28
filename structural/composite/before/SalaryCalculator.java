package structural.composite.before;

public class SalaryCalculator {
    
    // BAD: The calculator needs to know the exact structure and how to traverse it.
    // Violates Open/Closed Principle. If we add a new type (e.g., Freelancer Team), 
    // we have to modify this method.
    public double calculateTotalSalary(Department department) {
        double totalSalary = 0;
        
        // Sum salaries of direct employees
        for (Employee emp : department.getEmployees()) {
            totalSalary += emp.getSalary();
        }
        
        // Sum salaries of sub-departments (Recursive call)
        for (Department subDept : department.getSubDepartments()) {
            totalSalary += calculateTotalSalary(subDept);
        }
        
        return totalSalary;
    }

    // Notice we need a separate method if we just want an employee's salary.
    public double calculateEmployeeSalary(Employee employee) {
        return employee.getSalary();
    }
}
