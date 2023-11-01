package com.example.community.controller.response;

import com.example.community.service.dto.BoardDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class BoardDetailResponse {
  private final String publicId;
  private final String name;
  private final String description;
  private final OffsetDateTime createdDate;

  @JsonCreator
  private BoardDetailResponse(@JsonProperty("publicId") String publicId,
      @JsonProperty("name") String name, @JsonProperty("description") String description,
      @JsonProperty("createdDate") OffsetDateTime createdDate) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
  }

  public static BoardDetailResponse create(BoardDetailResponseDto dto) {
    return new BoardDetailResponse(dto.getPublicId(), dto.getName(), dto.getDescription(),
        dto.getCreatedDate());
  }
}
