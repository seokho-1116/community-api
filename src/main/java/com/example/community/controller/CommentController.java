package com.example.community.controller;

import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.controller.response.CommentCreateResponse;
import com.example.community.controller.response.CommentDeleteResponse;
import com.example.community.controller.response.CommentUpdateResponse;
import com.example.community.controller.response.PageCommentResponse;
import com.example.community.controller.response.PageResponse;
import com.example.community.service.CommentService;
import com.example.community.service.dto.CommentDetailResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{board_id}/posts/{post_id}")
public class CommentController {
  private final CommentService commentService;

  @GetMapping("/comments")
  public ResponseEntity<PageResponse> getComments(
      @PathVariable("post_id") UUID postPublicId, @RequestBody PageCommentRequest request) {
    Page<CommentDetailResponseDto> page = commentService.findComments(postPublicId,
        request.getPreviousDate(), request.getSize());

    return ResponseEntity.ok(PageCommentResponse.create(page));
  }

  @PostMapping("/comments")
  public ResponseEntity<CommentCreateResponse> createComment(
      @PathVariable("post_id") UUID postPublicId, @RequestBody CommentCreateRequest request) {
    UUID publicId = commentService.createComment(request.toDto(postPublicId));

    return ResponseEntity.ok(CommentCreateResponse.create(publicId));
  }

  @PatchMapping("/comments/{comment_id}")
  public ResponseEntity<CommentUpdateResponse> updateComment(
      @PathVariable("post_id") UUID postPublicId, @PathVariable("comment_id") UUID commentPublicId,
      @RequestBody CommentUpdateRequest request) {
    UUID publicId = commentService.updateComment(request.toDto(postPublicId, commentPublicId));

    return ResponseEntity.ok(CommentUpdateResponse.create(publicId));
  }

  @DeleteMapping("/comments/{comment_id}")
  public ResponseEntity<CommentDeleteResponse> deleteComment(
      @PathVariable("post_id") UUID postPublicId,
      @PathVariable("comment_id") UUID commentPublicId) {
    UUID publicId = commentService.deleteComment(postPublicId, commentPublicId);

    return ResponseEntity.ok(CommentDeleteResponse.create(publicId));
  }
}
