package com.example.community.controller;

import com.example.community.controller.response.ApiResponse;
import com.example.community.service.BoardService;
import com.example.community.service.dto.BoardDetailDto;
import com.example.community.service.dto.BoardSummaryDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
  private final BoardService boardService;

  @GetMapping
  public List<BoardSummaryDto> getAllBoards() {
    return boardService.findAllBoards();
  }

  @GetMapping("/{board_id}")
  public BoardDetailDto getBoardById(@PathVariable("board_id") String boardId) {
    return boardService.findBoardById(boardId);
  }
}
