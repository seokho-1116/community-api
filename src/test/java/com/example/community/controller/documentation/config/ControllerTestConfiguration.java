package com.example.community.controller.documentation.config;

import com.example.community.repository.MemberQueryRepository;
import com.example.community.controller.documentation.config.stub.StubMemberQueryRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.controller.documentation.config.stub.StubJwtFactory;
import com.example.community.security.config.SecurityConfig;
import com.example.community.controller.documentation.config.stub.StubTokenService;
import com.example.community.service.TokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(SecurityConfig.class)
public class ControllerTestConfiguration {
  public static final String dummySecretKey =
      "asdfasarspofjkosdfasdjkflikasndflkasndsdfjkadsnfkjasdn";

  @Bean
  public MemberQueryRepository memberQueryRepository() {
    return new StubMemberQueryRepository(null);
  }

  @Bean
  public JwtFactory jwtFactory() {
    return new StubJwtFactory(dummySecretKey);
  }

  @Bean
  public TokenService tokenService() {
    return new StubTokenService(null, null, null);
  }
}
