package com.example.community.security.authentication.jwt;

import java.util.Collection;
import java.util.UUID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private final UUID memberId;

  private JwtAuthenticationToken(UUID memberId) {
    super(null);
    this.memberId = memberId;
    setAuthenticated(false);
  }

  public JwtAuthenticationToken(UUID memberId,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.memberId = memberId;
  }

  public static JwtAuthenticationToken unauthenticated() {
    return new JwtAuthenticationToken(new UUID(0, 0));
  }

  public static JwtAuthenticationToken authenticated(UUID principal,
      Collection<? extends GrantedAuthority> authorities) {
    return new JwtAuthenticationToken(principal, authorities);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return memberId;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor");
    super.setAuthenticated(false);
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    if (o instanceof JwtAuthenticationToken) {
      JwtAuthenticationToken test = (JwtAuthenticationToken) o;
      return (this.getPrincipal() == test.getPrincipal());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.memberId.hashCode();
    return result;
  }
}
