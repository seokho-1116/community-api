package com.example.community.controller.response;

import com.example.community.service.dto.BoardSummaryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class BoardSummaryResponse {
  private final String publicId;
  private final String name;

  private BoardSummaryResponse(String publicId, String name) {
    this.publicId = publicId;
    this.name = name;
  }

  public static List<BoardSummaryResponse> create(List<BoardSummaryResponseDto> dtoList) {
    return dtoList.stream()
        .map(dto -> new BoardSummaryResponse(dto.getPublicId(), dto.getName()))
        .collect(Collectors.toList());
  }
}
