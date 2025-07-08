package com.nisum.hexagonal.infrastructure.output.adapter;

import com.nisum.hexagonal.application.output.port.H2BbPort;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.adapter.mapper.EntityMapper;
import com.nisum.hexagonal.infrastructure.output.repository.UserRepository;
import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class H2BbAdapter implements H2BbPort {

  private final UserRepository userRepository;
  private final EntityMapper entityMapper;

  @Override
  public User findOneByEmail(String email) {
    return entityMapper.toUser(userRepository.findOneByEmail(email));
  }

  @Override
  @Transactional
  public User save(User user) {
    UserEntity userEntity = entityMapper.toUserEntityWithPhones(user);
    var savedEntity = userRepository.save(userEntity);
    var phone = savedEntity.getPhoneEntities()
        .stream()
        .map(entityMapper::toPhone)
        .toList();
    var userTo = entityMapper.toUser(savedEntity);
    userTo.setPhones(phone);
    return userTo;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll()
        .stream()
        .map(getUserEntityUser())
        .toList();
  }

  private Function<UserEntity, User> getUserEntityUser() {
    return entity -> {
      var phones = entity.getPhoneEntities().stream()
          .map(entityMapper::toPhone)
          .toList();
      var user = entityMapper.toUser(entity);
      user.setPhones(phones);
      return user;
    };
  }

  @Override
  public void delete(User user) {
    userRepository.delete(entityMapper.toUserEntity(user));
  }
}
