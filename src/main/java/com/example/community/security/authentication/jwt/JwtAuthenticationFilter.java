package com.example.community.security.authentication.jwt;

import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtFactory jwtFactory;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String accessToken = getAccessTokenFromRequest(request);

    if (isNotValidToken(accessToken)) {
      throw new InvalidTokenException("jwt 토큰이 유효하지 않습니다.");
    }

    Authentication authentication = getAuthenticationToken(accessToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  private boolean isNotValidToken(String accessToken) {
    return !accessToken.isEmpty() && !jwtFactory.isValid(TokenType.ACCESS, accessToken);
  }

  private Authentication getAuthenticationToken(String accessToken) {
    if (!StringUtils.hasText(accessToken)) {
      return JwtAuthenticationToken.unauthenticated();
    }

    return createJwtAuthenticationTokenBy(accessToken);
  }

  private String getAccessTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      return token.substring(7);
    }

    return "";
  }

  private Authentication createJwtAuthenticationTokenBy(String accessToken) {
    String memberPublicId = jwtFactory.getPayload(TokenType.ACCESS, accessToken,
        "memberId");
    String role = jwtFactory.getPayload(TokenType.ACCESS, accessToken, "role");

    return JwtAuthenticationToken.authenticated(UUID.fromString(memberPublicId),
        List.of(new SimpleGrantedAuthority(role)));
  }
}
