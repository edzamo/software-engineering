package com.solid.principle.srp.example2.good;

public interface PasswordRepository {

  boolean savePasswordDB(String password);

  boolean savePasswordFile(String password, String fileName);
}
