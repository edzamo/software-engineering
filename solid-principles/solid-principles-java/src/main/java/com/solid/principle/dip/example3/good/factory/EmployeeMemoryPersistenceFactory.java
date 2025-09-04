package com.solid.principle.dip.example3.good.factory;

import com.solid.principle.dip.example3.good.persistence.EmployeeMemoryPersistence;
import com.solid.principle.dip.example3.good.persistence.EmployeePersistence;


public class EmployeeMemoryPersistenceFactory implements EmployeePersistenceFactory {

    @Override
    public EmployeePersistence getEmployeePersistence() {
        return new EmployeeMemoryPersistence();
    }
    
}
