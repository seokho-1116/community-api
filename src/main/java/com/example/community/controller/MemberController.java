package com.example.community.controller;

import com.example.community.controller.request.EmailUpdateRequest;
import com.example.community.controller.request.NicknameUpdateRequest;
import com.example.community.controller.request.PasswordUpdateRequest;
import com.example.community.controller.request.SignupRequest;
import com.example.community.controller.response.EmailUpdateResponse;
import com.example.community.controller.response.MemberDetailResponse;
import com.example.community.controller.response.NicknameUpdateResponse;
import com.example.community.controller.response.SignupResponse;
import com.example.community.service.MemberService;
import com.example.community.service.dto.MemberDetailDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
    UUID memberId = memberService.createMember(request.toDto());

    return ResponseEntity.ok(SignupResponse.create(memberId));
  }

  @GetMapping
  public ResponseEntity<MemberDetailResponse> getMember(@AuthenticationPrincipal UUID memberId) {
    MemberDetailDto dto = memberService.findMemberByPublicId(memberId);

    return ResponseEntity.ok(MemberDetailResponse.create(dto));
  }

  @PatchMapping("/email")
  public ResponseEntity<EmailUpdateResponse> updateEmail(@RequestBody EmailUpdateRequest request,
      @AuthenticationPrincipal UUID memberId) {
    String email = memberService.updateEmail(memberId, request.getEmail());

    return ResponseEntity.ok(EmailUpdateResponse.create(email));
  }

  @PatchMapping("/nickname")
  public ResponseEntity<NicknameUpdateResponse> updateNickname(
      @RequestBody NicknameUpdateRequest request, @AuthenticationPrincipal UUID memberId) {
    String nickname = memberService.updateNickname(memberId,request.getNickname());

    return ResponseEntity.ok(NicknameUpdateResponse.create(nickname));
  }

  @PatchMapping("/password")
  public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest request,
      @AuthenticationPrincipal UUID memberId) {
    memberService.updatePassword(memberId,request.getPassword());

    return ResponseEntity.ok().build();
  }
}
