package com.nisum.hexagonal.application.output.port;


import com.nisum.hexagonal.domain.User;
import java.util.List;

public interface H2BbPort {

  User findOneByEmail(String email);

  User save(User user);

  List<User> findAll();

  void delete(User user);
}
