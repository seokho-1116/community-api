package com.example.community.controller;

import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.controller.response.CommentCreateResponse;
import com.example.community.controller.response.CommentDeleteResponse;
import com.example.community.controller.response.CommentDetailResponse;
import com.example.community.controller.response.CommentUpdateResponse;
import com.example.community.controller.response.PageResponse;
import com.example.community.controller.response.factory.PageResponseFactory;
import com.example.community.service.CommentService;
import com.example.community.service.dto.CommentDetailDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: board id가 필요한가? 나중에 comment에 board id를 넣어야 한다.
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{board_id}/posts/{post_id}")
public class CommentController {
  private final CommentService commentService;

  @GetMapping("/comments")
  public ResponseEntity<PageResponse<CommentDetailResponse>> getComments(
      @PathVariable("post_id") String postId, @RequestBody PageCommentRequest request) {
    Page<CommentDetailDto> page = commentService.findComments(postId, request.getPreviousDate(),
        PageRequest.of(request.getPage(), request.getSize()));

    return ResponseEntity.ok(PageResponseFactory.createCommentsPageResponse(page));
  }

  @PostMapping("/comments")
  public ResponseEntity<CommentCreateResponse> createComment(@PathVariable("post_id") UUID postId,
      @RequestBody CommentCreateRequest request) {
    UUID publicId = commentService.createComment(request.toDto(postId));

    return ResponseEntity.ok(CommentCreateResponse.create(publicId));
  }

  @PatchMapping("/comments/{comment_id}")
  public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable("post_id") UUID postId,
      @PathVariable("comment_id") UUID commentId, @RequestBody CommentUpdateRequest request) {
    UUID publicId = commentService.updateComment(request.toDto(postId, commentId));

    return ResponseEntity.ok(CommentUpdateResponse.create(publicId));
  }

  @DeleteMapping("/comments/{comment_id}")
  public ResponseEntity<CommentDeleteResponse> deleteComment(@PathVariable("post_id") UUID postId,
      @PathVariable("comment_id") UUID commentId) {
    UUID publicId = commentService.deleteComment(postId, commentId);

    return ResponseEntity.ok(CommentDeleteResponse.create(publicId));
  }
}
