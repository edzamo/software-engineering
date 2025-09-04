package com.solid.principle.dip.example3.good.persistence;

import java.util.ArrayList;
import java.util.List;

import com.solid.principle.dip.example3.good.Employee;

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
