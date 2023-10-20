package com.example.community.security.authentication.login.handler;

import com.example.community.controller.response.ApiResponse;
import com.example.community.security.authentication.login.response.TokenResponse;
import com.example.community.security.userdetails.CustomUserDetails;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
  private final TokenService tokenService;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    ApiResponse<TokenResponse> apiResponseBody = makeResponse(customUserDetails);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter()
        .write(objectMapper.writeValueAsString(apiResponseBody));
  }

  private ApiResponse<TokenResponse> makeResponse(CustomUserDetails customUserDetails) {
    TokenResponseDto dto = tokenService.createToken(customUserDetails.getPublicId(),
        customUserDetails.getFirstAuthority());

    return ApiResponse.success(TokenResponse.create(dto));
  }
}
