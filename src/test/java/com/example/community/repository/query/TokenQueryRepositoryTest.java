package com.example.community.repository.query;

import static com.example.api.jooqgen.tables.Token.TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.TokenQueryRepository;
import com.example.community.service.dto.TokenDto;
import com.example.community.service.entity.Role;
import com.example.community.service.entity.Token;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TokenQueryRepositoryTest extends QueryRepositoryTest {
  private final TokenQueryRepository tokenQueryRepository;
  private final DSLContext dslContext;

  public TokenQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.tokenQueryRepository = new TokenQueryRepository(dslContext);
    this.dslContext = dslContext;
  }

  @Test
  void findTokenByPublicId() {
    Token token = Token.create("refresh", UUID.randomUUID(), Role.USER);

    dslContext
        .insertInto(TOKEN, TOKEN.ID, TOKEN.PUBLIC_ID, TOKEN.REFRESH_TOKEN,
            TOKEN.MEMBER_PUBLIC_ID, TOKEN.ROLE)
        .values(token.getId(), token.getPublicId(), token.getRefreshToken(),
            token.getMemberPublicId(), token.getRole().name())
        .execute();

    Optional<TokenDto> dto = tokenQueryRepository.findTokenByPublicId(token.getPublicId());

    assertThat(dto).isPresent();
  }
}