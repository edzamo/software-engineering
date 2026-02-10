package com.nisum.hexagonal.infrastructure.util;

import com.nisum.hexagonal.domain.Phone;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.repository.entity.PhoneEntity;
import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import java.util.List;
import org.springframework.lang.NonNull;


@SuppressWarnings("java:S1118")
public final class MockData {

  @NonNull
  public static User getUser() {
    return User.builder()
        .name("Juan Rodriguez")
        .email("juan@rodriguez.org")
        .password("Valid1@Password")
        .phones(List.of(
            Phone.builder()
                .number("1234567")
                .citycode("1")
                .contrycode("57")
                .build()
        ))
        .build();
  }

  @NonNull
  public static Phone getPhone() {
    return Phone.builder()
        .number("1234567")
        .citycode("1")
        .contrycode("57")
        .build();
  }


  @NonNull
  public static List<User> getUsers() {
    return List.of(User.builder()
        .name("Juan Rodriguez")
        .email("juan@rodriguez.org")
        .password("Valid1@Password")
        .build());
  }

  @NonNull
  public static UserEntity getUserEntity() {
    return UserEntity.builder()
        .name("example")
        .email("ezamo@xx.com")
        .phoneEntities(List.of(PhoneEntity.builder()
            .citycode("2")
            .contrycode("45")
            .build()))
        .build();
  }

}
