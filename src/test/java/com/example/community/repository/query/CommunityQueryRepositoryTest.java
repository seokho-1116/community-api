package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.CommunityQueryRepository;
import com.example.community.service.dto.CommunityDetailResponseDto;
import java.util.Optional;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommunityQueryRepositoryTest extends QueryRepositoryTest {
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
