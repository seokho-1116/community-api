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
import javax.validation.Valid;
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
  public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
    UUID memberPublicId = memberService.createMember(request.toDto());

    return ResponseEntity.ok(SignupResponse.create(memberPublicId));
  }

  @GetMapping
  public ResponseEntity<MemberDetailResponse> getMember(
      @AuthenticationPrincipal UUID memberPublicId) {
    MemberDetailDto dto = memberService.findMemberByPublicId(memberPublicId);

    return ResponseEntity.ok(MemberDetailResponse.create(dto));
  }

  @PatchMapping("/email")
  public ResponseEntity<EmailUpdateResponse> updateEmail(
      @RequestBody @Valid EmailUpdateRequest request,
      @AuthenticationPrincipal UUID memberPublicId) {
    String email = memberService.updateEmail(memberPublicId, request.getEmail());

    return ResponseEntity.ok(EmailUpdateResponse.create(email));
  }

  @PatchMapping("/nickname")
  public ResponseEntity<NicknameUpdateResponse> updateNickname(
      @RequestBody @Valid NicknameUpdateRequest request,
      @AuthenticationPrincipal UUID memberPublicId) {
    String nickname = memberService.updateNickname(memberPublicId, request.getNickname());

    return ResponseEntity.ok(NicknameUpdateResponse.create(nickname));
  }

  @PatchMapping("/password")
  public ResponseEntity<Void> updatePassword(@RequestBody @Valid PasswordUpdateRequest request,
      @AuthenticationPrincipal UUID memberPublicId) {
    memberService.updatePassword(memberPublicId, request.getPassword());

    return ResponseEntity.noContent().build();
  }
}
