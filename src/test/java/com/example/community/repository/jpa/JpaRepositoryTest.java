package com.example.community.repository.jpa;

import com.example.community.common.TestData;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@DirtiesContext
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract class JpaRepositoryTest {
  static final TestData TEST_DATA = new TestData();

  protected static final PostgreSQLContainer<?> postgresqlContainer =
      new PostgreSQLContainer<>("postgres:latest")
          .withReuse(true)
          .withCreateContainerCmdModifier(bingPort())
          .withDatabaseName("test-database")
          .withUsername("test-user")
          .withPassword("test-password");

  private static Consumer<CreateContainerCmd> bingPort() {
    return cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
        new PortBinding(Ports.Binding.bindPort(36918), new ExposedPort(5432))
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

    postgresqlContainer.stop();
  }
}
