package com.nisum.hexagonal.application.input.port;


import com.nisum.hexagonal.domain.User;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserPort {

  List<User> findAll();

  User saveUser(User user);
}
