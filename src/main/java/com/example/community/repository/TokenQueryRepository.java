package com.example.community.repository;

import static com.example.api.jooqgen.tables.Token.TOKEN;

import com.example.community.service.dto.TokenDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

//TODO: need test
@Repository
@RequiredArgsConstructor
public class TokenQueryRepository {
  private final DSLContext dslContext;

  public TokenDto findTokenByPublicId(UUID publicId) {
    return dslContext
        .select(TOKEN.REFRESH_TOKEN, TOKEN.MEMBER_PUBLIC_ID, TOKEN.ROLE)
        .from(TOKEN)
        .where(TOKEN.PUBLIC_ID.eq(publicId))
        .fetchOneInto(TokenDto.class);
  }
}
