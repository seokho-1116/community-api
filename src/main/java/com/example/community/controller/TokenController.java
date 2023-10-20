package com.example.community.controller;

import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.controller.response.TokenRefreshResponse;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenRefreshResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: need test
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenController {
  private final TokenService tokenService;

  @PostMapping("/token")
  public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
    TokenRefreshResponseDto dto = tokenService.refresh(request.getRefreshTokenPublicId());

    return ResponseEntity.ok(TokenRefreshResponse.create(dto));
  }
}
