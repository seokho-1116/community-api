package com.example.community.controller;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.response.PageResponse;
import com.example.community.controller.response.PostCategoryResponse;
import com.example.community.controller.response.PostCreateResponse;
import com.example.community.controller.response.PostDeleteResponse;
import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.controller.response.PostUpdateResponse;
import com.example.community.controller.response.factory.PageResponseFactory;
import com.example.community.controller.response.factory.PostResponseFactory;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostDetailDto;
import com.example.community.service.dto.PostSummaryDto;
import java.util.List;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class PostController {
  private final PostService postService;

  //TODO: 과연 페이지 번호가 필요한가?
  @GetMapping("/posts")
  public ResponseEntity<PageResponse<PostSummaryResponse>> getPosts(
      @RequestBody PagePostRequest request) {
    Page<PostSummaryDto> posts = postService.findPosts(request.getPreviousDate(),
        PageRequest.of(request.getPage(), request.getSize()));

    return ResponseEntity.ok(PageResponseFactory.createPostsPageResponse(posts));
  }

  @GetMapping("/{board_id}/posts")
  public ResponseEntity<PageResponse<PostSummaryResponse>> getPostsByBoardId(
      @PathVariable("board_id") String boarId, @RequestBody PagePostRequest request) {
    Page<PostSummaryDto> posts = postService.findPostsByBoardId(boarId, request.getPreviousDate(),
        PageRequest.of(request.getPage(), request.getSize()));

    return ResponseEntity.ok(PageResponseFactory.createPostsPageResponse(posts));
  }

  @GetMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostDetailResponse> getBoardPostByPostId(
      @PathVariable("board_id") String boardId, @PathVariable("post_id") String postId) {
    PostDetailDto dto = postService.findBoardPostByPostId(boardId, postId);

    return ResponseEntity.ok(PostResponseFactory.createPostDetailResponse(dto));
  }

  @PostMapping("/{board_id}/posts")
  public ResponseEntity<PostCreateResponse> createPost(@PathVariable("board_id") String boardId,
      @RequestBody PostCreateRequest request) {
    String publicId = postService.createNewPost(request.toDto(boardId));

    return ResponseEntity.ok(PostCreateResponse.create(publicId));
  }

  @PatchMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostUpdateResponse> updatePost(@PathVariable("board_id") String boardId,
      @PathVariable("post_id") String postId, @RequestBody PostUpdateRequest request) {
    String publicId = postService.updatePost(request.toDto(boardId, postId));

    return ResponseEntity.ok(PostUpdateResponse.create(publicId));
  }

  @DeleteMapping("/{board_id}/posts/{post_id}")
  public ResponseEntity<PostDeleteResponse> deletePost(@PathVariable("board_id") String boardId,
      @PathVariable("post_id") String postId) {
    String publicId = postService.deletePost(boardId, postId);

    return ResponseEntity.ok(PostDeleteResponse.create(publicId));
  }

  @GetMapping("/{board_id}/posts/categories")
  public ResponseEntity<List<PostCategoryResponse>> getPostCategoriesById(
      @PathVariable("board_id") String boardId) {
    return ResponseEntity.ok(PostCategoryResponse.create(
        postService.findPostCategoryById(boardId)));
  }
}
