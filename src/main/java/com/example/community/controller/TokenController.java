package com.example.community.controller;

import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenController {
  private final TokenService tokenService;

  @PostMapping("/token")
  public ResponseEntity<TokenResponse> refreshToken(
      @RequestBody @Valid TokenRefreshRequest request)
  {
    TokenResponseDto dto = tokenService.refresh(request.getRefreshTokenPublicId());

    return ResponseEntity.ok(TokenResponse.create(dto));
  }
}
