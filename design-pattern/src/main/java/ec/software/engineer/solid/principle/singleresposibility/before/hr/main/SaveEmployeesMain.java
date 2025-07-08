package ec.software.engineer.solid.principle.singleresposibility.before.hr.main;




import ec.software.engineer.solid.principle.singleresposibility.before.hr.persistence.EmployeeRepository;
import ec.software.engineer.solid.principle.singleresposibility.before.hr.personnel.Employee;

import java.util.List;

public class SaveEmployeesMain {
    public static void main(String[] args) {
        // Grab employees
        EmployeeRepository repository = new EmployeeRepository();
        List<Employee> employees = repository.findAll();

        // Save all
        for (Employee e : employees){
            Employee.save(e);
        }
    }
}
