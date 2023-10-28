package com.example.community.service;

import com.example.community.repository.PostCategoryQueryRepository;
import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCategoryService {
  private final PostCategoryQueryRepository postCategoryQueryRepository;

  public List<PostCategoryDto> findPostCategoryById(final UUID boardPublicId) {
    return postCategoryQueryRepository.findPostCategoryByBoardPublicId(boardPublicId);
  }
}
