package com.example.community.controller;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.response.PagePostResponse;
import com.example.community.controller.response.PostCreateResponse;
import com.example.community.controller.response.PostDeleteResponse;
import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostUpdateResponse;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/boards")
public class PostController {
  private final PostService postService;

  @GetMapping("/posts")
  public ResponseEntity<PagePostResponse> getPosts(
      @RequestBody @Valid final PagePostRequest request) {
    Page<PostSummaryResponseDto> page = postService.findPosts(request.getPreviousDate(),
        request.getSize());

    return ResponseEntity.ok(PagePostResponse.create(page));
  }

  @GetMapping("/{board_id}/posts")
  public ResponseEntity<PagePostResponse> getPostsByBoardId(
      @PathVariable("board_id") final UUID boardPublicId,
      @RequestBody @Valid final PagePostRequest request) {
    Page<PostSummaryResponseDto> page = postService.findPostsByBoardPublicId(boardPublicId,
        request.getPreviousDate(), request.getSize());

    return ResponseEntity.ok(PagePostResponse.create(page));
  }

  @GetMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostDetailResponse> getBoardPostByPostId(
      @PathVariable("board_id") final UUID boardPublicId,
      @PathVariable("post_id") final UUID postPublicId,
      @AuthenticationPrincipal final UUID memberPublicId) {
    PostDetailResponseDto dto = postService.findBoardPostByPostId(boardPublicId, postPublicId,
        memberPublicId);

    return ResponseEntity.ok(PostDetailResponse.create(dto));
  }

  @PostMapping("/{board_id}/posts")
  public ResponseEntity<PostCreateResponse> createPost(@PathVariable("board_id") final UUID boardId,
      @AuthenticationPrincipal final UUID memberPublicId,
      @RequestBody @Valid final PostCreateRequest request) {
    UUID publicId = postService.createNewPost(request.toDto(boardId, memberPublicId));

    return ResponseEntity.ok(PostCreateResponse.create(publicId));
  }

  @PatchMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostUpdateResponse> updatePost(@PathVariable("board_id") final UUID boardId,
      @PathVariable("post_id") final UUID postPublicId,
      @AuthenticationPrincipal final UUID memberPublicId,
      @RequestBody @Valid final PostUpdateRequest request) {
    UUID publicId = postService.updatePost(request.toDto(boardId, postPublicId, memberPublicId));

    return ResponseEntity.ok(PostUpdateResponse.create(publicId));
  }

  @DeleteMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostDeleteResponse> deletePost(
      @PathVariable("board_id") final UUID boardPublicId,
      @PathVariable("post_id") final UUID postPublicId,
      @AuthenticationPrincipal final UUID memberPublicId) {
    UUID publicId = postService.deletePost(boardPublicId, postPublicId, memberPublicId);

    return ResponseEntity.ok(PostDeleteResponse.create(publicId));
  }
}
