package com.solid.principle.dip.example3.good.persistence;

import java.util.ArrayList;
import java.util.List;

import com.solid.principle.dip.example3.good.Employee;



public class EmployeeMySqlPersistence implements EmployeePersistence {

    public EmployeeMySqlPersistence(String connectionString, String user, String password) {
        // Initialize MySQL database connection
    }

    @Override
    public List<Employee> getAllEmployees() {
        // Implementation for MySQL database
        return new ArrayList<>();
    }

    @Override
    public void save(Employee employee) {
        // Implementation for MySQL database
    }
}
