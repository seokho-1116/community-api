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
    UUID boardPublicId = UUID.fromString("c40cf43b-3466-4e15-b450-d8be1f648efe");

    Optional<BoardDetailResponseDto> board = boardQueryRepository.findBoardById(boardPublicId);

    assertThat(board).isPresent();
  }
}