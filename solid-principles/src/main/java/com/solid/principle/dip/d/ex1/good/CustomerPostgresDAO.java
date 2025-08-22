package com.solid.principle.dip.d.ex1.good;

import java.util.List;

import com.solid.principle.dip.d.ex1.bad.Customer;

public class CustomerPostgresDAO implements CustomerDAO{
    @Override
    public List<Customer> findAll() {
        return null;
    }
}
