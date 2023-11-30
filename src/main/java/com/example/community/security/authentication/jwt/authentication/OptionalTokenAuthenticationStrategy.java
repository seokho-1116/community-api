package com.example.community.security.authentication.jwt.authentication;

import com.example.community.security.authentication.jwt.JwtAuthenticationToken;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class OptionalTokenAuthenticationStrategy implements TokenAuthenticationStrategy {
  private final JwtFactory jwtFactory;

  @Override
  public Authentication authenticate(HttpServletRequest request) {
    String accessToken = getAccessTokenFromRequest(request);

    if (accessToken.isEmpty()) {
      return JwtAuthenticationToken.anonymous();
    }

    if (!jwtFactory.isValid(accessToken)) {
      throw new InvalidTokenException("jwt 토큰이 유효하지 않습니다.");
    }

    return createJwtAuthenticationTokenBy(accessToken);
  }

  private Authentication createJwtAuthenticationTokenBy(String accessToken) {
    String memberPublicId = jwtFactory.getPayload(accessToken, "memberId");
    String role = jwtFactory.getPayload(accessToken, "role");

    return JwtAuthenticationToken.authenticated(UUID.fromString(memberPublicId),
        List.of(new SimpleGrantedAuthority(role)));
  }
}
