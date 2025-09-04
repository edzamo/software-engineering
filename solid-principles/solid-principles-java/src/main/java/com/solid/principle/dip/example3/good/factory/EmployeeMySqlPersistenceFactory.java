package com.solid.principle.dip.example3.good.factory;

import com.solid.principle.dip.example3.good.persistence.EmployeeMySqlPersistence;
import com.solid.principle.dip.example3.good.persistence.EmployeePersistence;

public class EmployeeMySqlPersistenceFactory implements EmployeePersistenceFactory {

    @Override
    public EmployeePersistence getEmployeePersistence() {
        return new EmployeeMySqlPersistence("","","");
    }
}
