package com.example.community.acceptance;

import com.example.community.common.TestData;
import com.example.community.controller.response.ApiResponse;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@ComponentScan(
    basePackages = {"com.example.community"},
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)
)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class AcceptanceTest {
  @LocalServerPort
  private int port;

  static final TestData TEST_DATA = new TestData();

  protected static final PostgreSQLContainer<?> postgresqlContainer =
      new PostgreSQLContainer<>("postgres:latest")
          .withCreateContainerCmdModifier(bingPort())
          .withDatabaseName("test-database")
          .withUsername("test-user")
          .withPassword("test-password");

  private static Consumer<CreateContainerCmd> bingPort() {
    return cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
        new PortBinding(Ports.Binding.bindPort(36908), new ExposedPort(5432))
    ));
  }

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    registry.add("spring.datasource.password", postgresqlContainer::getPassword);
  }

  @BeforeAll
  static void setupAll() {
    postgresqlContainer.start();
  }

  @AfterAll
  static void setupData() {
    String url = postgresqlContainer.getJdbcUrl();
    String username = postgresqlContainer.getUsername();
    String password = postgresqlContainer.getPassword();

    try (Connection conn = DriverManager.getConnection(url, username, password)) {
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("truncate.sql"));
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
    } catch (SQLException ex) {
      throw new RuntimeException("data init error", ex);
    }
  }

  @BeforeEach
  void setupEach() {
    RestAssured.port = port;
  }

  protected  <T> T changeResponseType(ExtractableResponse<Response> response,
      TypeRef<ApiResponse<T>> typeRef) {
    ApiResponse<T> apiResponse = response.as(typeRef);

    return apiResponse.getData();
  }
}
