package ec.software.engineer.solid.principle.singleresposibility.after.hr.main;


import ec.software.engineer.solid.principle.singleresposibility.after.hr.logging.ConsoleLogger;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.persistence.EmployeeFileSerializer;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.persistence.EmployeeRepository;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.Employee;

import java.io.IOException;
import java.util.List;

public class SaveEmployeesMain {
    public static void main(String[] args) {
        // Grab employees
        EmployeeFileSerializer employeeFileSerializer = new EmployeeFileSerializer();
        ConsoleLogger consoleLogger = new ConsoleLogger();

        EmployeeRepository repository = new EmployeeRepository(employeeFileSerializer);
        List<Employee> employees = repository.findAll();

        // Save all
        for (Employee e : employees){
            try {
                repository.save(e);
                consoleLogger.writeInfo("Saved employee " + e.toString());
            } catch (IOException exception) {
                consoleLogger.writeError("Error saving employee", exception);
            }
        }
    }
}
