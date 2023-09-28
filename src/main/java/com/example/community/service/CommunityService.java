package com.example.community.service;

import com.example.community.repository.CommunityQueryRepository;
import com.example.community.service.dto.CommunityDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
  private final CommunityQueryRepository communityQueryRepository;

  public CommunityDetailDto findCommunity() {
    return communityQueryRepository.findCommunity();
  }
}
