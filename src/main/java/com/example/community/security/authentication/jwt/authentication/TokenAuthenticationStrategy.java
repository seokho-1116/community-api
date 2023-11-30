package com.example.community.security.authentication.jwt.authentication;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

public interface TokenAuthenticationStrategy {
  Authentication authenticate(HttpServletRequest request);

  default String getAccessTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      return token.substring(7);
    }

    return "";
  }
}
