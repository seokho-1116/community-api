package com.example.community.repository;

import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCategoryQueryRepository {
  private final DSLContext dslContext;

  public Optional<UUID> findPostCategoryIdByPublicId(UUID postCategoryPublicId) {
    return dslContext
        .select(POST_CATEGORY.ID)
        .from(POST_CATEGORY)
        .where(POST_CATEGORY.PUBLIC_ID.eq(postCategoryPublicId))
        .fetchOptionalInto(UUID.class);
  }
}
