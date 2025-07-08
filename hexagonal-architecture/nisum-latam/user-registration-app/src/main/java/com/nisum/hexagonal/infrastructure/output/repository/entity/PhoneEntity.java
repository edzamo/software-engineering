package com.nisum.hexagonal.infrastructure.output.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Table(name = "phone")
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class PhoneEntity {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_phone", insertable = false, updatable = false, nullable = false)
  private Long id;

  @NotBlank(message = "Number must not be empty")
  @Column(name = "number")
  private String number;

  @NotBlank(message = "Country code must not be empty")
  @Column(name = "city_code")
  private String citycode;

  @NotBlank(message = "City code must not be empty")
  @Column(name = "country_code")
  private String contrycode;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user", nullable = false)
  private UserEntity user;
  @Override
  public String toString() {
    return "PhoneEntity{" +
        "id=" + id +
        ", number='" + number + '\'' +
        ", cityCode='" + citycode + '\'' +
        ", countryCode='" + contrycode + '\'' +
        '}';
  }
}
