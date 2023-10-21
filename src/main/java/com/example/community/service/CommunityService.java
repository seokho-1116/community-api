package com.example.community.service;

import com.example.community.repository.CommunityQueryRepository;
import com.example.community.service.dto.CommunityDetailResponseDto;
import com.example.community.service.exception.CommunityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
  private final CommunityQueryRepository communityQueryRepository;

  public CommunityDetailResponseDto findCommunity() {
    return communityQueryRepository.findCommunity()
        .orElseThrow(CommunityNotFoundException::new);
  }
}
