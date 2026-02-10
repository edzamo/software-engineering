package com.nisum.hexagonal.infrastructure.input.adapter.rest.mapper;


import com.nisum.hexagonal.domain.Phone;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.PhoneResponse;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserRequest;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserResponse;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    builder = @Builder(disableBuilder = true)
)
public interface UserMapper {

  User toUser(UserRequest request);
  UserResponse toUserResponse(User user);
  PhoneResponse toPhoneResponse(Phone phone );


}
