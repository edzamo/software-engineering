package com.nisum.hexagonal.infrastructure.output.adapter;

import static com.nisum.hexagonal.infrastructure.util.MockData.getPhone;
import static com.nisum.hexagonal.infrastructure.util.MockData.getUser;
import static com.nisum.hexagonal.infrastructure.util.MockData.getUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.adapter.mapper.EntityMapper;
import com.nisum.hexagonal.infrastructure.output.repository.UserRepository;
import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class H2BbAdapterTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private EntityMapper entityMapper;
  @InjectMocks
  private H2BbAdapter h2BbAdapter;

  @Test
  void findAll() {
    var userEntity = getUserEntity();
    var phone = getPhone();
    var user = getUser();
    List<UserEntity> userEntities = List.of(userEntity);

    when(userRepository.findAll()).thenReturn(userEntities);
    when(entityMapper.toPhone(userEntity.getPhoneEntities().get(0))).thenReturn(phone);
    when(entityMapper.toUser(userEntity)).thenReturn(user);


    List<User> users = h2BbAdapter.findAll();

    assertEquals(1, users.size());
    assertEquals(user, users.get(0));

    // Verify: Verificación de interacciones
    verify(userRepository, times(1)).findAll();
    verify(entityMapper, times(1)).toUser(userEntity);
  }

  @Test
  void findOneByEmail() {

    String email = "test@example.com";
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(email);
    User expectedUser = new User();
    when(userRepository.findOneByEmail(email)).thenReturn(userEntity);
    when(entityMapper.toUser(userEntity)).thenReturn(expectedUser);

    User actualUser = h2BbAdapter.findOneByEmail(email);

    // Assert: Verificación de resultados
    assertEquals(expectedUser, actualUser);
    verify(userRepository).findOneByEmail(email);
    verify(entityMapper).toUser(userEntity);
  }

}