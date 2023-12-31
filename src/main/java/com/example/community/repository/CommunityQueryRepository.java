package com.example.community.repository;

import static com.example.api.jooqgen.tables.Community.COMMUNITY;

import com.example.community.service.dto.CommunityDetailResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityQueryRepository {
  private final DSLContext dslContext;

  public Optional<CommunityDetailResponseDto> findCommunity() {
    return dslContext
        .select(COMMUNITY.INTRODUCTION, COMMUNITY.COMPANY_INFO, COMMUNITY.CONTACT_INFO,
            COMMUNITY.PRIVACY_POLICY, COMMUNITY.TERMS, COMMUNITY.ADS_INFO)
        .from(COMMUNITY)
        .fetchOptionalInto(CommunityDetailResponseDto.class);
  }
}
