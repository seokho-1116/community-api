package com.example.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.service.dto.CommunityDetailResponseDto;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
class CommunityQueryRepositoryTest {
  private final CommunityQueryRepository communityQueryRepository;

  public CommunityQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.communityQueryRepository = new CommunityQueryRepository(dslContext);
  }

  @Test
  void findCommunity() {
    Optional<CommunityDetailResponseDto> community = communityQueryRepository.findCommunity();

    assertThat(community).isPresent();
  }
}
