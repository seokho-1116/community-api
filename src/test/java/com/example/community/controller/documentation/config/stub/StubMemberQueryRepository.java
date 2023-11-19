package com.example.community.controller.documentation.config.stub;

import com.example.community.common.TestData;
import com.example.community.repository.MemberQueryRepository;
import com.example.community.security.userdetails.MemberAuthenticationDto;
import com.example.community.service.entity.Role;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StubMemberQueryRepository extends MemberQueryRepository {
  private final TestData testData = new TestData();
  private final PasswordEncoder passwordEncoder = PasswordEncoderFactories
      .createDelegatingPasswordEncoder();

  public StubMemberQueryRepository(DSLContext dslContext) {
    super(dslContext);
  }

  @Override
  public Optional<MemberAuthenticationDto> findMemberAuthenticationDtoBySignupId(String signupId) {
    return Optional.of(new MemberAuthenticationDto(testData.getMemberSignupId(),
        passwordEncoder.encode(testData.getMemberSignupPassword()),
        UUID.randomUUID(), Role.USER));
  }
}
