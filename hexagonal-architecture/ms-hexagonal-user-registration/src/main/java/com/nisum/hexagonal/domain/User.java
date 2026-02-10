package com.nisum.hexagonal.domain;

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
public class User {

  String id;
  String name;
  String email;
  String password;
  LocalDateTime created;
  LocalDateTime modified;
  LocalDateTime lastLogin;
  String token;
  boolean isactive;
  List<Phone> phones;
}
