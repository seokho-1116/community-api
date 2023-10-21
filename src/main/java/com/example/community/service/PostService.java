package com.example.community.service;

import com.example.community.repository.PostJpaRepository;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostCategoryDto;
import com.example.community.service.dto.PostCreateDto;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import com.example.community.service.dto.PostUpdateDto;
import com.example.community.service.entity.Post;
import com.example.community.service.exception.PostNotExistException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostQueryRepository postQueryRepository;
  private final PostJpaRepository postJpaRepository;

  public Page<PostSummaryResponseDto> findPosts(final OffsetDateTime previousDate, final int size) {
    return postQueryRepository.findPosts(previousDate, size);
  }

  public Page<PostSummaryResponseDto> findPostsByBoardId(final UUID boardId,
      final OffsetDateTime previousDate, final int size) {
    return postQueryRepository.findPostsByBoardId(boardId, previousDate, size);
  }

  public PostDetailResponseDto findBoardPostByPostId(final UUID boardId, final UUID postId) {
    return postQueryRepository.findBoardPostByPostId(boardId, postId)
        .orElseThrow(PostNotExistException::new);
  }

  public String createNewPost(final PostCreateDto dto) {
    Post post = dto.toEntity();

    postJpaRepository.save(post);

    return post.getPublicId().toString();
  }

  public String updatePost(final PostUpdateDto dto) {
    return postQueryRepository.updatePost(dto);
  }

  public String deletePost(final UUID boardId, final UUID postId) {
    return postQueryRepository.deletePost(boardId, postId);
  }

  public List<PostCategoryDto> findPostCategoryById(final UUID boardId) {
    return postQueryRepository.findPostCategoryById(boardId);
  }
}