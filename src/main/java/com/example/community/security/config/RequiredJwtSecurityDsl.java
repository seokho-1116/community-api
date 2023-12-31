package com.example.community.security.config;

import com.example.community.security.authentication.jwt.JwtAuthenticationFilter;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.authentication.RequiredTokenAuthenticationStrategy;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class RequiredJwtSecurityDsl extends
    AbstractHttpConfigurer<RequiredJwtSecurityDsl, HttpSecurity> {
  private final JwtFactory jwtFactory;

  @Override
  public void configure(HttpSecurity http) {
    http.requestMatchers(request -> request
        .antMatchers("/api/me", "/api/me/nickname", "/api/me/email", "/api/me/password")
        .antMatchers(HttpMethod.POST,"/api/boards/**/posts/**")
        .antMatchers(HttpMethod.PATCH,"/api/boards/**/posts/**")
        .antMatchers(HttpMethod.DELETE,"/api/boards/**/posts/**")
        .antMatchers(HttpMethod.POST, "/api/**/comments")
        .antMatchers(HttpMethod.PATCH, "/api/**/comments")
        .antMatchers(HttpMethod.DELETE, "/api/**/comments")
    ).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private Filter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(new RequiredTokenAuthenticationStrategy(jwtFactory));
  }

  public static RequiredJwtSecurityDsl jwtSecurityDsl(JwtFactory jwtFactory) {
    return new RequiredJwtSecurityDsl(jwtFactory);
  }
}
