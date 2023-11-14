package com.example.community.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.community.repository.MemberJpaRepository;
import com.example.community.service.entity.Member;
import com.example.community.service.exception.NotResourceOwnerException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberServiceTest extends ServiceTest {
  private static final UUID MEMBER_PUBLIC_ID = UUID.fromString(TEST_DATA.getMemberPublicId());
  private static final UUID NULL_UUID = TEST_DATA.getNullUUID();

  @InjectMocks
  private MemberService memberService;

  @Mock
  private MemberJpaRepository memberJpaRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @DisplayName("회원_권한이_있는_유저로_닉네임_업데이트_테스트")
  @Test
  void updateNicknameWithResourceOwner() {
    String newNickname = "new nick";
    when(memberJpaRepository.findMemberByPublicId(MEMBER_PUBLIC_ID))
        .thenReturn(createMember());

    String updatedNickname = memberService.updateNickname(MEMBER_PUBLIC_ID, newNickname);

    assertThat(updatedNickname).isEqualTo(newNickname);
  }

  private Member createMember() {
    return Member.builder()
        .publicId(MEMBER_PUBLIC_ID)
        .build();
  }

  @DisplayName("회원_권한이_없는_유저로_닉네임_업데이트_테스트")
  @Test
  void updateNicknameWithNotResourceOwner() {
    String newNickname = "new nick";
    when(memberJpaRepository.findMemberByPublicId(any(UUID.class)))
        .thenReturn(createMember());

    assertThatThrownBy(() -> memberService.updateNickname(NULL_UUID, newNickname))
        .isInstanceOf(NotResourceOwnerException.class);
  }

  @DisplayName("회원_권한이_있는_유저로_이메일_업데이트_테스트")
  @Test
  void updateEmailWithResourceOwner() {
    String newEmail = "new email";
    when(memberJpaRepository.findMemberByPublicId(any(UUID.class)))
        .thenReturn(createMember());

    String updatedEmail = memberService.updateEmail(MEMBER_PUBLIC_ID, newEmail);

    assertThat(updatedEmail).isEqualTo(newEmail);
  }

  @DisplayName("회원_권한이_없는_유저로_이메일_업데이트_테스트")
  @Test
  void updateEmailWithNotResourceOwner() {
    String newEmail = "new email";
    when(memberJpaRepository.findMemberByPublicId(any(UUID.class)))
        .thenReturn(createMember());

    assertThatThrownBy(() -> memberService.updateEmail(NULL_UUID, newEmail))
        .isInstanceOf(NotResourceOwnerException.class);
  }

  @DisplayName("회원_권한이_있는_유저로_패스워드_업데이트_테스트")
  @Test
  void updatePasswordWithResourceOwner() {
    String newPassword = "new password";
    when(memberJpaRepository.findMemberByPublicId(any(UUID.class)))
        .thenReturn(createMember());
    when(passwordEncoder.encode(any(CharSequence.class)))
        .thenReturn(newPassword);

    assertThatNoException().isThrownBy(() -> memberService.updatePassword(MEMBER_PUBLIC_ID,
        newPassword));
  }

  @DisplayName("회원_권한이_없는_유저로_패스워드_업데이트_테스트")
  @Test
  void updatePasswordWithNotResourceOwner() {
    String newPassword = "new password";
    when(memberJpaRepository.findMemberByPublicId(any(UUID.class)))
        .thenReturn(createMember());

    assertThatThrownBy(() -> memberService.updatePassword(NULL_UUID, newPassword))
        .isInstanceOf(NotResourceOwnerException.class);
  }
}