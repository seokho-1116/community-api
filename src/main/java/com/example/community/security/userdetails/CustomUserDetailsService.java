package com.example.community.security.userdetails;

import com.example.community.repository.MemberQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberQueryRepository memberQueryRepository;

  @Override
  public UserDetails loadUserByUsername(String signupId) throws UsernameNotFoundException {
    MemberAuthenticationDto dto = memberQueryRepository.findMemberAuthenticationDtoBySignupId(signupId)
        .orElseThrow(() -> new UsernameNotFoundException("user not exist"));

    return new CustomUserDetails(dto.getSignupId(), dto.getSignupPassword(),
        dto.getPublicId(), List.of(dto.getRole().toGrantedAuthority()));
  }
}
