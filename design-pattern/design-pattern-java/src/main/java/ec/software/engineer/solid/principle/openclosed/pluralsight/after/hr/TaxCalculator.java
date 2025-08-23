package ec.software.engineer.solid.principle.openclosed.pluralsight.after.hr;

import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.Employee;

public interface TaxCalculator {
    double calculate(Employee employee);
}
