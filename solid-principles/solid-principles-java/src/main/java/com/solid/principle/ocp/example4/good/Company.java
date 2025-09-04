package com.solid.principle.ocp.example4.good;

import java.util.List;

import lombok.Data;

@Data
public class Company {

    private EmployeeMemoryPersistence persistence;

    public List<Employee> getAllEmployees() {
        return persistence.getAllEmployees();
    }

    public void addEmployee(Employee employee) {
        persistence.save(employee);

    }
}
