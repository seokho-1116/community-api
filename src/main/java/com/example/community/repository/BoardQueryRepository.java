package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;

import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
  private final DSLContext dslContext;

  public List<BoardSummaryResponseDto> findAllBoards() {
    return dslContext
        .select(BOARD.PUBLIC_ID, BOARD.NAME)
        .from(BOARD)
        .fetchInto(BoardSummaryResponseDto.class);
  }

  public Optional<BoardDetailResponseDto> findBoardByPublicId(UUID boardPublicId) {
    return dslContext
        .select(BOARD.PUBLIC_ID, BOARD.NAME, BOARD.DESCRIPTION, BOARD.CREATED_DATE)
        .from(BOARD)
        .where(BOARD.PUBLIC_ID.eq(boardPublicId))
        .fetchOptionalInto(BoardDetailResponseDto.class);
  }

  public Optional<UUID> findBoardIdByPublicId(UUID boardPublicId) {
    return dslContext
        .select(BOARD.ID)
        .from(BOARD)
        .where(BOARD.PUBLIC_ID.eq(boardPublicId))
        .fetchOptionalInto(UUID.class);
  }
}
