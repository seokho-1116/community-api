package com.example.community.security.config;

import static com.example.community.security.config.CommonSecurityDsl.commonSecurityDsl;
import static com.example.community.security.config.JwtSecurityDsl.jwtSecurityDsl;
import static com.example.community.security.config.LoginSecurityDsl.loginSecurityDsl;

import com.example.community.repository.MemberQueryRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.login.handler.LoginFailureHandler;
import com.example.community.security.authentication.login.handler.LoginSuccessHandler;
import com.example.community.security.userdetails.CustomUserDetailsService;
import com.example.community.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final MemberQueryRepository memberQueryRepository;
  private final TokenService tokenService;
  private final JwtFactory jwtFactory;
  private final ObjectMapper objectMapper;

  @Bean
  @Order(1)
  public SecurityFilterChain filterChainForJwt(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    http.apply(commonSecurityDsl());
    http.apply(jwtSecurityDsl(jwtFactory));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChainForLogin(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    http.authenticationProvider(daoAuthenticationProvider(userDetailsService()));

    http.apply(commonSecurityDsl());
    http.apply(loginSecurityDsl(authenticationSuccessHandler(),
        authenticationFailureHandler(), objectMapper));

    return http.build();
  }

  public AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(bcryPasswordEncoder());

    return provider;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService(memberQueryRepository);
  }

  private AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new LoginSuccessHandler(tokenService, objectMapper);
  }

  private AuthenticationFailureHandler authenticationFailureHandler() {
    return new LoginFailureHandler(objectMapper);
  }

  @Bean
  public PasswordEncoder bcryPasswordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
