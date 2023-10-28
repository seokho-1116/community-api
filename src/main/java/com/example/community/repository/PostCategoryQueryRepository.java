package com.example.community.repository;

import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;

import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
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

  public List<PostCategoryDto> findPostCategoryByBoardPublicId(final UUID boardPublicId) {
    return dslContext
        .select(POST_CATEGORY.NAME, POST_CATEGORY.DESCRIPTION)
        .from(POST_CATEGORY)
        .where(POST_CATEGORY.BOARD_PUBLIC_ID.eq(boardPublicId))
        .fetchInto(PostCategoryDto.class);
  }
}
