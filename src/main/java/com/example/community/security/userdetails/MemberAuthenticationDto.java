package com.example.community.security.userdetails;

import com.example.community.service.entity.Role;
import java.beans.ConstructorProperties;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MemberAuthenticationDto {
  private final String signupId;
  private final String signupPassword;
  private final UUID publicId;
  private final Role role;

  @ConstructorProperties({"signupId", "signupPassword", "publicId", "role"})
  public MemberAuthenticationDto(String signupId, String signupPassword, UUID publicId,
      Role role) {
    this.signupId = signupId;
    this.signupPassword = signupPassword;
    this.publicId = publicId;
    this.role = role;
  }
}
