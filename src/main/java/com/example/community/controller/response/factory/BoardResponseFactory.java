package com.example.community.controller.response.factory;

import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import com.example.community.service.dto.BoardDetailDto;
import com.example.community.service.dto.BoardSummaryDto;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponseFactory {

  public static List<BoardSummaryResponse> createBoardsSummaryResponse(List<BoardSummaryDto> boards) {
    return boards.stream()
        .map(board -> new BoardSummaryResponse(board.getPublicId(), board.getName()))
        .collect(Collectors.toList());
  }

  public static BoardDetailResponse createBoardDetailResponse(BoardDetailDto board) {
    return new BoardDetailResponse(board.getPublicId(), board.getName(), board.getDescription(),
        board.getCreatedDate());
  }
}
