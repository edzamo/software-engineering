package com.nisum.hexagonal.infrastructure.input.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @JsonIgnore
  private String user;

  @JsonIgnore
  private String pass;

  private String token;

}