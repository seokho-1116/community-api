package com.example.community.repository;

import static com.example.api.jooqgen.tables.Member.MEMBER;

import com.example.community.security.userdetails.MemberAuthenticationDto;
import com.example.community.service.dto.MemberDetailDto;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
  private final DSLContext dslContext;

  public Optional<MemberAuthenticationDto> findMemberAuthenticationDtoBySignupId(String signupId) {
    return dslContext
        .select(MEMBER.SIGNUP_ID, MEMBER.SIGNUP_PASSWORD, MEMBER.PUBLIC_ID, MEMBER.ROLE)
        .from(MEMBER)
        .where(MEMBER.SIGNUP_ID.eq(signupId))
        .fetchOptionalInto(MemberAuthenticationDto.class);
  }

  public Optional<MemberDetailDto> findMemberDetailDtoByPublicId(UUID memberPublicId) {
    return dslContext
        .select(MEMBER.SIGNUP_ID, MEMBER.NICKNAME, MEMBER.CREATED_DATE, MEMBER.EMAIL)
        .from(MEMBER)
        .where(MEMBER.PUBLIC_ID.eq(memberPublicId))
        .fetchOptionalInto(MemberDetailDto.class);
  }

  public Optional<UUID> findMemberIdByPublicId(UUID memberPublicId) {
    return dslContext
        .select(MEMBER.ID)
        .from(MEMBER)
        .where(MEMBER.PUBLIC_ID.eq(memberPublicId))
        .fetchOptionalInto(UUID.class);
  }
}
