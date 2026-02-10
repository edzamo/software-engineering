package com.nisum.hexagonal.infrastructure.output.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Table(name = "app_user")
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_user", insertable = false, updatable = false, nullable = false)
  private UUID id;

  @NotBlank(message = "Name may not be empty")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "Email may not be empty")
  @Column(name = "email")
  private String email;


  @NotBlank(message = "User password may not be empty.")
  @Column(name = "password")
  private String password;

  @Column(name = "created")
  private LocalDateTime created;


  @Column(name = "modified")
  private LocalDateTime modified;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "token")
  private String token;


  @Column(name = "isactive")
  private boolean isactive;


  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
  @JsonIgnore
  private List<PhoneEntity> phoneEntities;

  @PrePersist
  public void insertDateCreate() {
    created = LocalDateTime.now();
    lastLogin = created;
  }
  @Override
  public String toString() {
    return "UserEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", isActive=" + isactive +
        ", created=" + created +
        ", modified=" + modified +
        ", lastLogin=" + lastLogin +
        ", token='" + token + '\'' +
        '}';
  }
}
