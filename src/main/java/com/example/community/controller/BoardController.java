package com.example.community.controller;

import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import com.example.community.service.BoardService;
import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import java.util.List;
import java.util.UUID;
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
    List<BoardSummaryResponseDto> dtoList = boardService.findAllBoards();

    return ResponseEntity.ok(BoardSummaryResponse.create(dtoList));
  }

  @GetMapping("/{board_id}")
  public ResponseEntity<BoardDetailResponse> getBoardByPublicId(
      @PathVariable("board_id") final UUID boardPublicId) {
    BoardDetailResponseDto dto = boardService.findBoardByPublicId(boardPublicId);

    return ResponseEntity.ok(BoardDetailResponse.create(dto));
  }
}
