package com.nisum.hexagonal.application.service;


import com.nisum.hexagonal.application.input.port.UserPort;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.exception.EmailAlreadyRegisteredException;
import com.nisum.hexagonal.infrastructure.output.adapter.H2BbAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

  private final H2BbAdapter h2BbAdapter;
  @Value("${password.pattern}")
  private String patternPassword;


  @Override
  public List<User> findAll() {
    return h2BbAdapter.findAll();
  }

  @Override
  public User saveUser(User user) {

    if (validateEmail(user.getEmail())) {
      throw new EmailAlreadyRegisteredException("El correo ya registrado");
    }
    user.setPhones(user.getPhones().stream()
        .peek(p -> p.setUser(user))
            .toList());
    return h2BbAdapter.save(user);
  }

  private boolean validateEmail(String email) {
    return h2BbAdapter.findOneByEmail(email) != null;
  }


}
