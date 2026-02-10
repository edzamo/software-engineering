package com.nisum.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class UserEntityResponseRegistrationAppApplicationTests {
  @Autowired
  private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertNotNull(applicationContext);
  }

  @Test
  void testCustomOpenAPI() {
    OpenAPI openAPI = applicationContext.getBean(OpenAPI.class);
    assertNotNull(openAPI);
    assertEquals("Nisum Spring Boot 3 API ", openAPI.getInfo().getTitle());
    assertEquals("1.0.1", openAPI.getInfo().getVersion());
    assertEquals("Sample app Spring Boot 3 with Swagger", openAPI.getInfo().getDescription());
    assertEquals("http://swagger.io/terms/", openAPI.getInfo().getTermsOfService());
    assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
    assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
  }

  
  @Test
  void testGroupedOpenApi() {
    GroupedOpenApi groupedOpenApi = applicationContext.getBean(GroupedOpenApi.class);
    assertNotNull(groupedOpenApi);
    assertEquals("public", groupedOpenApi.getGroup());
    assertTrue(groupedOpenApi.getPathsToMatch().contains("/api/**"));
  }

}
