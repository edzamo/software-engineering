package com.nisum.hexagonal.infrastructure.input.adapter.rest.impl;

import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.User;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.security.JWTAuthtenticationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

  private final JWTAuthtenticationConfig jwtAuthtenticationConfig;

  @PostMapping("login")
  public User login(@RequestParam("user") String username, @RequestParam("encryptedPass") String encryptedPass) {

    String token = jwtAuthtenticationConfig.getJWTToken(username);
    return new User(username, encryptedPass, token);

  }

}