package com.nisum.hexagonal.application.service;

import static com.nisum.hexagonal.infrastructure.util.MockData.getUsers;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.adapter.H2BbAdapter;
import com.nisum.hexagonal.infrastructure.output.adapter.mapper.EntityMapper;
import com.nisum.hexagonal.infrastructure.output.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserAdapterTest {
  @Mock
  private H2BbAdapter h2BbAdapter;

  @Mock
  private EntityMapper entityMapper;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserAdapter userAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void testFindAll() {
    // Arrange
    List<User> userList = getUsers();
    when(h2BbAdapter.findAll()).thenReturn(userList);
    // Act
    List<User> result = userAdapter.findAll();
    // Assert
    assertNotNull(result);

  }

}