package com.example.community.controller;

import com.example.community.controller.response.PostCategoryResponse;
import com.example.community.service.PostCategoryService;
import com.example.community.service.dto.PostCategoryDto;
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
@RequestMapping("/api/boards/{board_id}/posts")
public class PostCategoryController {
  private final PostCategoryService postCategoryService;

  @GetMapping("/categories")
  public ResponseEntity<List<PostCategoryResponse>> getPostCategoriesById(
      @PathVariable("board_id") final UUID boardPublicId) {
    List<PostCategoryDto> dtoList = postCategoryService.findPostCategoryById(boardPublicId);

    return ResponseEntity.ok(PostCategoryResponse.create(dtoList));
  }
}
