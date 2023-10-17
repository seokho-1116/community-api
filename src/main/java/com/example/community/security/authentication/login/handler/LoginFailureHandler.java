package com.example.community.security.authentication.login.handler;

import com.example.community.controller.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
  private static final String LOGIN_FAIL_MESSAGE = "아이디나 비밀번호가 올바르지 않습니다.";
  private static final String CHARSET = "utf-8";

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {

    ApiResponse<String> fail = ApiResponse.fail(LOGIN_FAIL_MESSAGE);

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setCharacterEncoding(CHARSET);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    response.getWriter().write(objectMapper.writeValueAsString(fail));
  }
}
