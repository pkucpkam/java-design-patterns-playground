package structural.composite.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structural.composite.after.Department;
import structural.composite.after.Employee;
import structural.composite.after.OrganizationComponent;

import static org.junit.jupiter.api.Assertions.*;

class CompositePatternTest {

    private OrganizationComponent headOffice;
    private OrganizationComponent engineering;
    private OrganizationComponent hr;

    @BeforeEach
    void setUp() {
        headOffice = new Department("Head Office");
        engineering = new Department("Engineering");
        hr = new Department("Human Resources");

        headOffice.add(engineering);
        headOffice.add(hr);
    }

    @Test
    void testEmployeeSalary_HappyPath() {
        OrganizationComponent dev = new Employee("Alice", "Developer", 3000);
        assertEquals(3000, dev.calculateSalary(), "Employee salary should match the set value.");
    }

    @Test
    void testDepartmentSalary_HappyPath() {
        OrganizationComponent dev1 = new Employee("Alice", "Developer", 3000);
        OrganizationComponent dev2 = new Employee("Bob", "Developer", 2800);
        engineering.add(dev1);
        engineering.add(dev2);

        assertEquals(5800, engineering.calculateSalary(), "Department salary should be the sum of its employees' salaries.");
    }

    @Test
    void testNestedDepartmentSalary_HappyPath() {
        OrganizationComponent dev = new Employee("Alice", "Developer", 3000);
        engineering.add(dev);

        OrganizationComponent recruiter = new Employee("Diana", "Recruiter", 2500);
        hr.add(recruiter);

        assertEquals(5500, headOffice.calculateSalary(), "Head office salary should be the sum of all sub-departments.");
    }

    @Test
    void testEmptyDepartment_EdgeCase() {
        OrganizationComponent emptyDept = new Department("Empty Dept");
        assertEquals(0, emptyDept.calculateSalary(), "Empty department should have 0 salary.");
    }

    @Test
    void testRemoveComponent_HappyPath() {
        OrganizationComponent dev = new Employee("Alice", "Developer", 3000);
        engineering.add(dev);
        assertEquals(3000, engineering.calculateSalary());

        engineering.remove(dev);
        assertEquals(0, engineering.calculateSalary(), "Department salary should be 0 after removing the only employee.");
    }

    @Test
    void testAddOnLeaf_FailureCase() {
        OrganizationComponent dev = new Employee("Alice", "Developer", 3000);
        OrganizationComponent dev2 = new Employee("Bob", "Developer", 2800);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            dev.add(dev2);
        });
        assertEquals("Cannot add to a leaf component.", exception.getMessage());
    }

    @Test
    void testRemoveOnLeaf_FailureCase() {
        OrganizationComponent dev = new Employee("Alice", "Developer", 3000);
        OrganizationComponent dev2 = new Employee("Bob", "Developer", 2800);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            dev.remove(dev2);
        });
        assertEquals("Cannot remove from a leaf component.", exception.getMessage());
    }
}
