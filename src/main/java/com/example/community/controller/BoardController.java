package com.example.community.controller;

import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import com.example.community.controller.response.factory.BoardResponseFactory;
import com.example.community.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<BoardSummaryResponse>> getAllBoards() {
    return ResponseEntity.ok(BoardResponseFactory.createBoardsSummaryResponse(
        boardService.findAllBoards()
    ));
  }

  @GetMapping("/{board_id}")
  public ResponseEntity<BoardDetailResponse> getBoardById(
      @PathVariable("board_id") String boardId) {
    return ResponseEntity.ok(BoardResponseFactory.createBoardDetailResponse(
        boardService.findBoardById(boardId)
    ));
  }
}
