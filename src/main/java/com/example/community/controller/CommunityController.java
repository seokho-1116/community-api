package com.example.community.controller;

import com.example.community.controller.response.CommunityDetailResponse;
import com.example.community.service.CommunityService;
import com.example.community.service.dto.CommunityDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
  private final CommunityService communityService;

  @GetMapping
  public ResponseEntity<CommunityDetailResponse> getCommunity() {
    CommunityDetailResponseDto dto = communityService.findCommunity();

    return ResponseEntity.ok(CommunityDetailResponse.create(dto));
  }
}
