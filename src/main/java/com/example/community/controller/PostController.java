package com.example.community.controller;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.response.PageResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.controller.response.factory.PageResponseFactory;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class PostController {
  private final PostService postService;

  @GetMapping("/posts")
  public ResponseEntity<PageResponse<PostSummaryResponse>> getPosts(
      @RequestBody PagePostRequest pagePostRequest) {
    Page<PostSummaryDto> posts = postService.findPosts(pagePostRequest.getPreviousDate(),
        pagePostRequest.getSize());

    return ResponseEntity.ok(PageResponseFactory.createPostsPageResponse(posts));
  }
}
