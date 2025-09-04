package com.solid.principle.ocp.example4.good;

import java.util.List;

public interface EmployeePersistence {
    List<Employee> getAllEmployees();
    void save(Employee employee);
}
