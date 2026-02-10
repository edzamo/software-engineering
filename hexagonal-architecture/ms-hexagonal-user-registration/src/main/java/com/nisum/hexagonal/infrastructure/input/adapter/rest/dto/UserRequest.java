package com.nisum.hexagonal.infrastructure.input.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserRequest {

  String name;
  @NotBlank(message = "Email must not be empty")
  @Email(message = "Email should be valid")
  @Pattern(
      regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "Email should be a valid email address"
  )
  String email;
  @NotBlank(message = "Password must not be empty")
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
      message = "Password must be between 8 and 20 characters, include at least one digit, one lowercase letter, one uppercase letter, and one special character."
  )
  String password;

  String token;
  List<PhoneRequest> phones;
}
