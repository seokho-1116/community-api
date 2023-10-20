package com.example.community.security.authentication.login;

import com.example.community.security.authentication.login.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
  private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
      new AntPathRequestMatcher("/api/auth/login", "POST");

  private final ObjectMapper objectMapper;

  public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
    super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException {
    LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
        .unauthenticated(loginRequest.getSignupId(), loginRequest.getSignupPassword());

    return super.getAuthenticationManager().authenticate(authRequest);
  }
}
