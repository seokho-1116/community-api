package com.example.community.service.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
  USER, ADMIN;

  public GrantedAuthority toGrantedAuthority() {
    return new SimpleGrantedAuthority(this.toString());
  }
}
