package com.example.community.security.authentication.login.handler;

import com.example.community.controller.response.ApiResponse;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.login.response.TokenResponse;
import com.example.community.security.userdetails.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtFactory jwtFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

    ApiResponse<TokenResponse> apiResponse = createResponse(principal.getPublicId(),
        getRole(principal));

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
  }

  private ApiResponse<TokenResponse> createResponse(String memberId, String role) {
    TokenResponse response = TokenResponse.create(
        jwtFactory.createAccessToken(memberId, role), jwtFactory.createRefreshToken(memberId,role),
        jwtFactory.getExpiresIn());

    return ApiResponse.success(response);
  }

  private String getRole(UserDetails principal) {
    List<GrantedAuthority> authorities = (List<GrantedAuthority>) principal.getAuthorities();

    return authorities.get(0).getAuthority();
  }
}
