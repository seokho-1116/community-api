package com.example.community.service;

import com.example.community.repository.BoardQueryRepository;
import com.example.community.service.dto.BoardDetailDto;
import com.example.community.service.dto.BoardSummaryDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardQueryRepository boardQueryRepository;

  public List<BoardSummaryDto> findAllBoards() {
    return boardQueryRepository.findAllBoards();
  }

  public BoardDetailDto findBoardById(String boardId) {
    return boardQueryRepository.findBoardById(boardId);
  }
}
