package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.BoardQueryRepository;
import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BoardQueryRepositoryTest extends QueryRepositoryTest {
  private final BoardQueryRepository boardQueryRepository;

  public BoardQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.boardQueryRepository = new BoardQueryRepository(dslContext);
  }

  @DisplayName("커뮤니티의_모든_게시판_조회_쿼리_테스트")
  @Test
  void findAllBoards() {
    List<BoardSummaryResponseDto> boards = boardQueryRepository.findAllBoards();

    assertThat(boards).isNotEmpty();
  }

  @DisplayName("공개_키로_게시판_정보_조회_쿼리_테스트")
  @Test
  void findBoardById() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getBoardPublicId());

    Optional<BoardDetailResponseDto> board = boardQueryRepository.findBoardByPublicId(boardPublicId);

    assertThat(board).isPresent();
  }
}