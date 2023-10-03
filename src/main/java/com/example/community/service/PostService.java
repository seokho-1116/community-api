package com.example.community.service;

import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostDetailDto;
import com.example.community.service.dto.PostSummaryDto;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostQueryRepository postQueryRepository;

  public Page<PostSummaryDto> findPosts(String previousDate, Pageable pageable) {
    return postQueryRepository.findPosts(OffsetDateTime.parse(previousDate), pageable);
  }

  public Page<PostSummaryDto> findPostsByBoardId(String boardId, String previousDate,
      Pageable pageable) {
    return postQueryRepository.findPostsByBoardId(UUID.fromString(boardId),
        OffsetDateTime.parse(previousDate), pageable);
  }

  public PostDetailDto findBoardPostByPostId(String boardId, String postId) {
    return postQueryRepository.findBoardPostByPostId(UUID.fromString(boardId),
        UUID.fromString(postId));
  }
}
