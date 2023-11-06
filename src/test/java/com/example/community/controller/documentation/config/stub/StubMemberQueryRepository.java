package com.example.community.controller.documentation.config.stub;

import com.example.community.repository.MemberQueryRepository;
import com.example.community.security.userdetails.MemberAuthenticationDto;
import com.example.community.service.entity.Role;
import java.util.Optional;
import org.jooq.DSLContext;

public class StubMemberQueryRepository extends MemberQueryRepository {
  public StubMemberQueryRepository(DSLContext dslContext) {
    super(dslContext);
  }

  @Override
  public Optional<MemberAuthenticationDto> findMemberAuthenticationDtoBySignupId(String signupId) {
    return Optional.of(new MemberAuthenticationDto("id",
        "{bcrypt}$2a$10$MEPcZ3gNbwaQzeVMifFjzecIXvbxmYeCoBuOAOPCqnA2jm3G/crMK",
        "publicId", Role.USER));
  }
}
