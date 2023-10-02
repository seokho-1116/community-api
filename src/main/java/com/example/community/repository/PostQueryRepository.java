package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static com.example.api.jooqgen.tables.Post.POST;
import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;

import com.example.community.service.dto.PostSummaryDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
  private final DSLContext dslContext;

  public Page<PostSummaryDto> findPosts(String previousId, int size) {
    List<PostSummaryDto> dtos = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, MEMBER.NICKNAME, POST.VIEWS_COUNT,
            POST.POST_CATEGORY_ID, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.PUBLIC_ID.gt(UUID.fromString(previousId)))
        .limit(size)
        .fetchInto(PostSummaryDto.class);

    int count = dslContext.fetchCount(POST);

    return new PageImpl<>(dtos, Pageable.ofSize(size), count);
  }
}
