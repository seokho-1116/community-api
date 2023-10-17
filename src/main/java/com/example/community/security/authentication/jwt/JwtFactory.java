package com.example.community.security.authentication.jwt;

import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {
  private static final String ISSUER = "community";

  private final SecretKey key;
  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  public JwtFactory(JwtProperties jwtProperties) {
    this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    this.accessTokenValidityMs = jwtProperties.getAccessTokenValidityMs();
    this.refreshTokenValidityMs = jwtProperties.getRefreshTokenValidityMs();
  }

  public String createAccessToken(String memberId, String role) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + accessTokenValidityMs);

    return Jwts.builder()
        .claim("memberId", memberId)
        .claim("role", role)
        .setSubject(TokenType.ACCESS.name())
        .setIssuer(ISSUER)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String createRefreshToken(String memberId, String role) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + refreshTokenValidityMs);

    return Jwts.builder()
        .claim("memberId", memberId)
        .claim("role", role)
        .setSubject(TokenType.REFRESH.name())
        .setIssuer(ISSUER)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getPayload(final TokenType type, final String token, final String claimName) {
    try {
      return jwtParser(type.name())
          .parseClaimsJws(token)
          .getBody()
          .get(claimName, String.class);
    } catch (final JwtException | IllegalArgumentException e) {
      throw new InvalidTokenException("유효하지 않은 토큰입니다.");
    }
  }

  public boolean isValid(final TokenType type, final String token) {
    try {
      jwtParser(type.name())
          .parseClaimsJws(token);

      return true;
    } catch (final JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private JwtParser jwtParser(String subject) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .requireSubject(subject)
        .requireIssuer(ISSUER)
        .build();
  }

  public String getExpiresIn() {
    return String.valueOf(accessTokenValidityMs);
  }
}
