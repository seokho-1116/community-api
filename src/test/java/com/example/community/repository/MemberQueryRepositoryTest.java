package com.example.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.security.userdetails.MemberAuthenticationDto;
import com.example.community.service.dto.MemberDetailDto;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
class MemberQueryRepositoryTest {
  private final MemberQueryRepository memberQueryRepository;

  @Autowired
  MemberQueryRepositoryTest(DSLContext dslContext) {
    this.memberQueryRepository = new MemberQueryRepository(dslContext);
  }

  @Test
  void findMemberAuthenticationDtoBySignupIdTest() {
    String signupId = "brooke35@example.com";

    MemberAuthenticationDto dto = memberQueryRepository.findMemberAuthenticationDtoBySignupId(
        signupId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }

  @Test
  void findMemberDetailDtoByPublicIdTest() {
    UUID publicId = UUID.fromString("7da7aa39-d829-43ac-a3f1-b770d8deff1e");

    MemberDetailDto dto = memberQueryRepository.findMemberDetailDtoByPublicId(publicId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }
}