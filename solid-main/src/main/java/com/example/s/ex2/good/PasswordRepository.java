package com.example.s.ex2.good;

public interface PasswordRepository {

  boolean savePasswordDB(String password);

  boolean savePasswordFile(String password, String fileName);
}
