package com.solid.principle.ocp.example4.good;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMemoryPersistence implements EmployeePersistence{

    private List<Employee> employees;

    public EmployeeMemoryPersistence() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void save(Employee employee) {
        employees.add(employee);
    }   
    
}
