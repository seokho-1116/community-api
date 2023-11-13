package com.example.community.security.config;

import com.example.community.security.authentication.jwt.JwtAuthenticationFilter;
import com.example.community.security.authentication.jwt.JwtFactory;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityDsl  extends AbstractHttpConfigurer<JwtSecurityDsl, HttpSecurity> {
  private final JwtFactory jwtFactory;

  @Override
  public void configure(HttpSecurity http) {
    http.requestMatchers(request -> request
        .antMatchers("/api/me", "/api/me/nickname", "/api/me/email", "/api/me/password")
        .antMatchers("/api/boards/**/posts/**", "/api/boards/**/posts/**")
        .antMatchers("/api/**/comments")
        .antMatchers("/api/auth/token")
    ).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private Filter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtFactory);
  }

  public static JwtSecurityDsl jwtSecurityDsl(JwtFactory jwtFactory) {
    return new JwtSecurityDsl(jwtFactory);
  }
}
