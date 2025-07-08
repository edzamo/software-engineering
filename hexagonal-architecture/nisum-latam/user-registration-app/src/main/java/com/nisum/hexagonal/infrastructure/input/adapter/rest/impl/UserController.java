package com.nisum.hexagonal.infrastructure.input.adapter.rest.impl;


import com.nisum.hexagonal.application.service.UserAdapter;
import com.nisum.hexagonal.infrastructure.exception.UserRegistrationException;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserRequest;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserResponse;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.mapper.UserMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserMapper userMapper;
  private final UserAdapter userAdapter;

  @PostMapping("/user")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request,
      @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
    log.info("|-> ⏰ Register User start ::{} ", request);
    getToken(request, authorizationHeader);
    return Optional.of(request)
        .map(userMapper::toUser)
        .map(userAdapter::saveUser)
        .map(user -> {
          var phoneResponse = user.getPhones()
              .stream()
              .map(userMapper::toPhoneResponse)
              .collect(Collectors.toList());
          var userResponse = userMapper.toUserResponse(user);
          userResponse.setPhone(phoneResponse);
          return userResponse;
        })
        .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.CREATED))
        .orElseThrow(() -> new UserRegistrationException("User registration failed"));
  }

  private static void getToken(UserRequest request, String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);
      request.setToken(token);
    }
  }

  @GetMapping("/user")
  public ResponseEntity<List<UserResponse>> findAll() {
    log.info("|-> ⏰ findAll Users start!. ");
    return Optional.of(userAdapter.findAll())
        .map(users -> {
          return users.stream().map(user -> {
            var phonesResponse = user.getPhones().stream()
                .map(userMapper::toPhoneResponse)
                .collect(Collectors.toList());
            var userResponse = userMapper.toUserResponse(user);
            userResponse.setPhone(phonesResponse);
            return userResponse;
          }).collect(Collectors.toList());
        })
        .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
        .orElseThrow(() -> new UserRegistrationException("User registration failed"));
  }

}
