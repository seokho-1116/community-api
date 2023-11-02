package com.example.community.controller.intergration;

import com.example.community.controller.response.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@TestConfiguration
@ComponentScan(
    basePackages = {"com.example.community"},
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)
)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class IntegrationTest {
  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
  }

  protected  <T> T changeResponseType(ExtractableResponse<Response> response,
      TypeRef<ApiResponse<T>> typeRef) {
    ApiResponse<T> apiResponse = response.as(typeRef);

    return apiResponse.getData();
  }
}
