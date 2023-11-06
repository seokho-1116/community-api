package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.TokenQueryRepository;
import com.example.community.service.dto.TokenDto;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TokenQueryRepositoryTest extends QueryRepositoryTest {
  private final TokenQueryRepository tokenQueryRepository;

  public TokenQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.tokenQueryRepository = new TokenQueryRepository(dslContext);
  }

  @Test
  void findTokenByPublicId() {
    UUID refreshTokenPublicId = UUID.fromString("e37fec2d-9e56-4049-a65a-b8eb44575f1f");

    Optional<TokenDto> dto = tokenQueryRepository.findTokenByPublicId(refreshTokenPublicId);

    assertThat(dto).isPresent();
  }
}