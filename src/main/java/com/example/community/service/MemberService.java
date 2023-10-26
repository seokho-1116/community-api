package com.example.community.service;

import com.example.community.repository.MemberJpaRepository;
import com.example.community.repository.MemberQueryRepository;
import com.example.community.service.dto.MemberDetailDto;
import com.example.community.service.dto.SignupRequestDto;
import com.example.community.service.entity.Member;
import com.example.community.service.exception.MemberNotFoundException;
import com.example.community.service.exception.NotResourceOwnerException;
import java.util.UUID;
import liquibase.pro.packaged.P;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
  private final MemberJpaRepository memberJpaRepository;
  private final MemberQueryRepository memberQueryRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UUID createMember(SignupRequestDto dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());

    Member entity = dto.toEntity(encodedPassword);

    memberJpaRepository.save(entity);

    return entity.getPublicId();
  }

  @Transactional
  public String updateNickname(UUID memberPublicId, String nickname) {
    Member member = memberJpaRepository.findMemberByPublicId(memberPublicId);

    if (member.isNotOwner(memberPublicId)) {
      throw NotResourceOwnerException.ofMember();
    }

    member.changeNickname(nickname);

    return member.getNickname();
  }

  @Transactional
  public String updateEmail(UUID memberPublicId, String email) {
    Member member = memberJpaRepository.findMemberByPublicId(memberPublicId);

    if (member.isNotOwner(memberPublicId)) {
      throw NotResourceOwnerException.ofMember();
    }

    member.changeEmail(email);

    return member.getEmail();
  }

  @Transactional
  public void updatePassword(UUID memberPublicId, String password) {
    Member member = memberJpaRepository.findMemberByPublicId(memberPublicId);

    if (member.isNotOwner(memberPublicId)) {
      throw NotResourceOwnerException.ofMember();
    }

    String encodedPassword = passwordEncoder.encode(password);

    member.changePassword(encodedPassword);
  }

  public MemberDetailDto findMemberByPublicId(UUID memberPublicId) {
      return memberQueryRepository.findMemberDetailDtoByPublicId(memberPublicId)
        .orElseThrow(MemberNotFoundException::new);
  }
}
