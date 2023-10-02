package com.example.community.service;

import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostQueryRepository postQueryRepository;

  public Page<PostSummaryDto> findPosts(String previousId, int size) {
    return postQueryRepository.findPosts(previousId, size);
  }
}
