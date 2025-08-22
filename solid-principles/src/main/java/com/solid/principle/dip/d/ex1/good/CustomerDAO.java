package com.solid.principle.dip.d.ex1.good;

import java.util.List;

import com.solid.principle.dip.d.ex1.bad.Customer;

public interface CustomerDAO {

    List<Customer> findAll();
}
