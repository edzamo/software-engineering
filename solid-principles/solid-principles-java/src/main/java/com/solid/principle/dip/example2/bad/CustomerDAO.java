package com.solid.principle.dip.example2.bad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDAO {

    private MysqlDataSource dataSource;

    public CustomerDAO() {
        this.dataSource = new MysqlDataSource();
        this.dataSource.setServerName("localhost");
        this.dataSource.setPortNumber(3306);
        this.dataSource.setDatabaseName("javajdbc");
        this.dataSource.setUser("root");
        this.dataSource.setPassword("password");
    }

    public List<Customer> findAll(){
        try(Connection connection = dataSource.getConnection();){


        } catch (SQLException sqle) {

        }
        return new ArrayList<>();
    }
}
