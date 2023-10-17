package com.example.community.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class CommonSecurityDsl extends AbstractHttpConfigurer<CommonSecurityDsl, HttpSecurity> {
  @Override
  public void init(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .httpBasic().disable()
        .formLogin().disable();
  }

  public static CommonSecurityDsl commonSecurityDsl() {
    return new CommonSecurityDsl();
  }
}
