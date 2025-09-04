package com.solid.principle.dip.example3.good.persistence;

import java.util.List;

import com.solid.principle.dip.example3.good.Employee;

public interface EmployeePersistence {
    public List<Employee> getAllEmployees();

    public void save(Employee employee);
}
