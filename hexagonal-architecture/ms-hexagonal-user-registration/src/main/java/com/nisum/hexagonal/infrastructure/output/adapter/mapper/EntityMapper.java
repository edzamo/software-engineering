package com.nisum.hexagonal.infrastructure.output.adapter.mapper;


import com.nisum.hexagonal.domain.Phone;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.repository.entity.PhoneEntity;
import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true)
)
public interface EntityMapper {

  User toUser(UserEntity entity);
  @Mapping(target = "phoneEntities", source = "phones")
  UserEntity toUserEntity(User user);
  @Mapping(target = "user", ignore = true) // Ignorar el mapeo de 'user' en PhoneEntity
  PhoneEntity toPhoneEntity(Phone phone);
  Phone toPhone(PhoneEntity entity);
  default UserEntity toUserEntityWithPhones(User user) {
    UserEntity userEntity = toUserEntity(user);
    List<PhoneEntity> phoneEntities = user.getPhones().stream()
        .map(phone -> {
          PhoneEntity phoneEntity = toPhoneEntity(phone);
          phoneEntity.setUser(userEntity);
          return phoneEntity;
        }).toList();
    userEntity.setPhoneEntities(phoneEntities);

    return userEntity;
  }



}
