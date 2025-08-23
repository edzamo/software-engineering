package com.solid.principle.dip.example2.good;

import java.util.List;

import com.solid.principle.dip.example2.bad.Customer;

public interface CustomerDAO {

    List<Customer> findAll();
}
