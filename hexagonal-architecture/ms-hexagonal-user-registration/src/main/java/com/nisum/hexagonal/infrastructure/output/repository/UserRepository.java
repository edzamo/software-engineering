package com.nisum.hexagonal.infrastructure.output.repository;

import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  UserEntity findOneByEmail(String email);
  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.phoneEntities")
  List<UserEntity> findAllWithPhones();
}
