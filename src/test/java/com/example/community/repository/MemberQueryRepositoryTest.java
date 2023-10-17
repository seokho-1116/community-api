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
    String signupId = "23f797cb-838b-4451-950d-f8ac8f88b4c6";

    MemberAuthenticationDto dto = memberQueryRepository.findMemberAuthenticationDtoBySignupId(
        signupId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }

  @Test
  void findMemberDetailDtoByPublicIdTest() {
    UUID publicId = UUID.fromString("cfd8be94-42d9-49b4-b021-5b0da1945c5a");

    MemberDetailDto dto = memberQueryRepository.findMemberDetailDtoByPublicId(publicId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }
}