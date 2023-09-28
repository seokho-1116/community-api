package com.example.community.controller;

import com.example.community.controller.response.ApiResponse;
import com.example.community.service.CommunityService;
import com.example.community.service.dto.CommunityDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
  private final CommunityService communityService;

  @GetMapping
  public ResponseEntity<ApiResponse<CommunityDetailDto>> getCommunity() {
    CommunityDetailDto dto = communityService.findCommunity();

    return ResponseEntity.ok(ApiResponse.success(dto));
  }
}
