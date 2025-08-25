package com.solid.principle.dip.example2.good;

import java.util.List;

import com.solid.principle.dip.example2.bad.Customer;

public class CustomerPostgresDAO implements CustomerDAO{
    @Override
    public List<Customer> findAll() {
        return null;
    }
}
