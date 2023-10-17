package com.example.community.security.config;

import com.example.community.security.authentication.login.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginSecurityDsl extends AbstractHttpConfigurer<LoginSecurityDsl, HttpSecurity> {
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  private final ObjectMapper objectMapper;

  @Override
  public void configure(HttpSecurity http) {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

    http.requestMatchers(request -> request
        .antMatchers("/api/me/login")
    ).addFilterBefore(loginFilter(authenticationManager),
        UsernamePasswordAuthenticationFilter.class);
  }

  private Filter loginFilter(AuthenticationManager authenticationManager) {
    LoginFilter filter = new LoginFilter(authenticationManager, objectMapper);

    filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    filter.setAuthenticationFailureHandler(authenticationFailureHandler);

    return filter;
  }

  public static LoginSecurityDsl loginSecurityDsl(
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler, ObjectMapper objectMapper) {
    return new LoginSecurityDsl(authenticationSuccessHandler, authenticationFailureHandler,
        objectMapper);
  }
}
