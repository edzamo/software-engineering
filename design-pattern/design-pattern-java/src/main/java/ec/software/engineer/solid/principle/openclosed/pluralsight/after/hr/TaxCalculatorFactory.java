package ec.software.engineer.solid.principle.openclosed.pluralsight.after.hr;


import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.Employee;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.FullTimeEmployee;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.Intern;
import ec.software.engineer.solid.principle.singleresposibility.after.hr.personnel.PartTimeEmployee;

public class TaxCalculatorFactory {
    public static TaxCalculator create(Employee employee) {
        if (employee instanceof FullTimeEmployee) {
            return new FullTimeTaxCalculator();
        }

        if (employee instanceof PartTimeEmployee) {
            return new PartTimeTaxCalculator();
        }

        if (employee instanceof Intern) {
            return new InternTaxCalculator();
        }

        throw new RuntimeException("Invalid employee type");
    }
}
