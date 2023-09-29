package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;

import com.example.community.service.dto.BoardDetailDto;
import com.example.community.service.dto.BoardSummaryDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
  private final DSLContext dslContext;

  public List<BoardSummaryDto> findAllBoards() {
    return dslContext.select(BOARD.PUBLIC_ID, BOARD.NAME)
        .from(BOARD)
        .fetchInto(BoardSummaryDto.class);
  }

  public BoardDetailDto findBoardById(String boardId) {
    return dslContext.select(BOARD.PUBLIC_ID, BOARD.NAME, BOARD.DESCRIPTION, BOARD.CREATED_DATE)
        .from(BOARD)
        .fetchOneInto(BoardDetailDto.class);
  }
}
