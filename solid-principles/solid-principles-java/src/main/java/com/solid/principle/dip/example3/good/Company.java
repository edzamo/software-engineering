package com.solid.principle.dip.example3.good;

import java.util.List;

import com.solid.principle.dip.example3.good.factory.EmployeePersistenceFactory;
import com.solid.principle.dip.example3.good.persistence.EmployeePersistence;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Company {

    private EmployeePersistence persistence;

    public Company(EmployeePersistenceFactory factory) {
        this.persistence = factory.getEmployeePersistence();
    }

    public List<Employee> getAllEmployees() {
        return persistence.getAllEmployees();
    }

    public void addEmployee(Employee employee) {
        persistence.save(employee);

    }
}
