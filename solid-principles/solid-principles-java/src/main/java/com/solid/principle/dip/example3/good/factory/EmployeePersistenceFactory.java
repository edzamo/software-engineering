package com.solid.principle.dip.example3.good.factory;

import com.solid.principle.dip.example3.good.persistence.EmployeePersistence;

public interface  EmployeePersistenceFactory {

    public EmployeePersistence getEmployeePersistence();
    
}
