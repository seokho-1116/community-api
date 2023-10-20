package com.example.community.security.userdetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
  private final String signupId;
  private final String signupPassword;
  private final String publicId;
  private final List<GrantedAuthority> authorities;

  public CustomUserDetails(String signupId, String signupPassword,
      String publicId, List<GrantedAuthority> authorities) {
    this.signupId = signupId;
    this.signupPassword = signupPassword;
    this.publicId = publicId;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.unmodifiableList(authorities);
  }

  @Override
  public String getPassword() {
    return signupPassword;
  }

  @Override
  public String getUsername() {
    return signupId;
  }

  public String getPublicId() {
    return publicId;
  }

  public String getFirstAuthority() {
    return authorities.get(0).getAuthority();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
