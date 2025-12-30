package com.nisum.hexagonal.infrastructure.input.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
@Builder
public class UserResponse {

  String id;
  String name;
  String email;
  LocalDateTime created;
  LocalDateTime modified;
  LocalDateTime lastLogin;
  String token;
  boolean isactive;
  List<PhoneResponse> phone;
}
