package com.solid.principle.dip.d.ex1.good;

import java.util.List;

import com.solid.principle.dip.d.ex1.bad.Customer;

public class CustomerService {

    CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) { // Polimorfismo
        this.customerDAO = customerDAO;
    }

    public List<Customer> findAll(){
        return this.customerDAO.findAll();
    }
}
