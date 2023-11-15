package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.MemberQueryRepository;
import com.example.community.security.userdetails.MemberAuthenticationDto;
import com.example.community.service.dto.MemberDetailDto;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberQueryRepositoryTest extends QueryRepositoryTest {
  private final MemberQueryRepository memberQueryRepository;

  @Autowired
  MemberQueryRepositoryTest(DSLContext dslContext) {
    this.memberQueryRepository = new MemberQueryRepository(dslContext);
  }

  @DisplayName("로그인_아이디로_회원_인증_dto_조회_쿼리_테스트")
  @Test
  void findMemberAuthenticationDtoBySignupIdTest() {
    String signupId = TEST_DATA.getMemberSignupId();

    MemberAuthenticationDto dto = memberQueryRepository.findMemberAuthenticationDtoBySignupId(
        signupId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }

  @DisplayName("공개_키로_회원_상세_정보_조회_쿼리_테스트")
  @Test
  void findMemberDetailDtoByPublicIdTest() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());

    MemberDetailDto dto = memberQueryRepository.findMemberDetailDtoByPublicId(publicId)
        .orElseThrow();

    assertThat(dto).isNotNull();
  }
}