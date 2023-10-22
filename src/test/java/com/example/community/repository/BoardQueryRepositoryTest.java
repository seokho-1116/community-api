package com.example.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
class BoardQueryRepositoryTest {
  private final BoardQueryRepository boardQueryRepository;

  public BoardQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.boardQueryRepository = new BoardQueryRepository(dslContext);
  }

  @Test
  void findAllBoards() {
    List<BoardSummaryResponseDto> boards = boardQueryRepository.findAllBoards();

    assertThat(boards).isNotEmpty();
  }

  @Test
  void findBoardById() {
    UUID boardPublicId = UUID.fromString("8f712b3f-bdf2-4261-bacb-9d224b05a6e8");

    Optional<BoardDetailResponseDto> board = boardQueryRepository.findBoardByPublicId(boardPublicId);

    assertThat(board).isPresent();
  }
}