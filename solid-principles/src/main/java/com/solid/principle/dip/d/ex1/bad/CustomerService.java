package com.solid.principle.dip.d.ex1.bad;

import java.util.List;

public class CustomerService {

    CustomerDAO customerDAO = new CustomerDAO(); // acoplado

    public List<Customer> findAll(){
        return this.customerDAO.findAll();
    }
}
