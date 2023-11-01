package com.example.community.controller.response;

import com.example.community.service.dto.BoardSummaryResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import liquibase.pro.packaged.J;
import lombok.Getter;

@Getter
public class BoardSummaryResponse {
  private final String publicId;
  private final String name;

  @JsonCreator
  private BoardSummaryResponse(@JsonProperty("publicId") String publicId,
      @JsonProperty("name") String name) {
    this.publicId = publicId;
    this.name = name;
  }

  public static List<BoardSummaryResponse> create(List<BoardSummaryResponseDto> dtoList) {
    return dtoList.stream()
        .map(dto -> new BoardSummaryResponse(dto.getPublicId(), dto.getName()))
        .collect(Collectors.toList());
  }
}
