package com.example.community.security.config;

import com.example.community.security.authentication.jwt.JwtAuthenticationFilter;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.authentication.OptionalTokenAuthenticationStrategy;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class OptionalJwtSecurityDsl extends
    AbstractHttpConfigurer<RequiredJwtSecurityDsl, HttpSecurity> {
  private final JwtFactory jwtFactory;

  @Override
  public void configure(HttpSecurity http) {
    http.requestMatchers(request -> request
        .antMatchers(HttpMethod.GET, "/api/boards/**/posts/**")
        .antMatchers(HttpMethod.GET,"/api/**/comments")
    ).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private Filter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(new OptionalTokenAuthenticationStrategy(jwtFactory));
  }

  public static OptionalJwtSecurityDsl jwtSecurityDsl(JwtFactory jwtFactory) {
    return new OptionalJwtSecurityDsl(jwtFactory);
  }
}
