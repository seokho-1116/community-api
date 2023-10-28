package com.example.community.service;

import com.example.community.repository.BoardQueryRepository;
import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import com.example.community.service.exception.BoardNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardQueryRepository boardQueryRepository;

  public List<BoardSummaryResponseDto> findAllBoards() {
    return boardQueryRepository.findAllBoards();
  }

  public BoardDetailResponseDto findBoardByPublicId(final UUID boardPublicId) {
    return boardQueryRepository.findBoardByPublicId(boardPublicId)
        .orElseThrow(BoardNotFoundException::new);
  }
}
