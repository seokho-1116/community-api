package com.example.community.controller.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.MemberController;
import com.example.community.controller.documentation.fieldsfactory.request.MemberRequestFieldsFactory;
import com.example.community.controller.request.EmailUpdateRequest;
import com.example.community.controller.request.NicknameUpdateRequest;
import com.example.community.controller.request.PasswordUpdateRequest;
import com.example.community.controller.request.SignupRequest;
import com.example.community.controller.documentation.fieldsfactory.response.MemberResponseFieldsFactory;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.service.MemberService;
import com.example.community.service.dto.MemberDetailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = MemberController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class MemberControllerTest extends RestDocsTestSetup {
  private static final String DUMMY_JWT = "Bearer token";

  @MockBean
  private MemberService memberService;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("회원가입_문서_테스트")
  @Test
  void signup() throws Exception {
    //given
    SignupRequest request = createTestSingupRequest();
    UUID publicId = UUID.randomUUID();

    when(memberService.createMember(any())).thenReturn(publicId);

    //when
    ResultActions response = mockMvc.perform(post("/api/me/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestFields(MemberRequestFieldsFactory.memberCreateResponseFields()),
            responseFields(MemberResponseFieldsFactory.memberCreateResponseFields())
        ));
  }

  private SignupRequest createTestSingupRequest() {
    return new SignupRequest("id", "nickname", "password",
        "id@email.com");
  }

  @DisplayName("회원_정보_조회_문서_테스트")
  @Test
  void getMember() throws Exception {
    when(memberService.findMemberByPublicId(any())).thenReturn(createTestMemberDetailDto());

    //when
    ResultActions response = mockMvc.perform(get("/api/me")
        .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            responseFields(MemberResponseFieldsFactory.getMemberDetailResponseField())
        ));
  }

  private MemberDetailDto createTestMemberDetailDto() {
    return new MemberDetailDto("signupId",
        "nickname", "email", OffsetDateTime.now());
  }

  @DisplayName("멤버_이메일_업데이트_문서_테스트")
  @Test
  void updateEmail() throws Exception {
    //given
    EmailUpdateRequest request = createTestEmailUpdateRequest();

    when(memberService.updateEmail(any(), any())).thenReturn(request.getEmail());

    //when
    ResultActions response = mockMvc.perform(patch("/api/me/email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .header("Authorization", DUMMY_JWT)
    );

    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(MemberRequestFieldsFactory.memberEmailUpdateRequestFields()),
            responseFields(MemberResponseFieldsFactory.memberEmailUpdateResponseFields())
        ));
  }

  private EmailUpdateRequest createTestEmailUpdateRequest() {
    return new EmailUpdateRequest("new@email.com");
  }

  @DisplayName("멤버_닉네임_업데이트_문서_테스트")
  @Test
  void updateNickname() throws Exception {
    //given
    NicknameUpdateRequest request = createTestNicknameUpdateRequest();

    //when
    when(memberService.updateNickname(any(), any())).thenReturn(request.getNickname());


    //then
    ResultActions response = mockMvc.perform(patch("/api/me/nickname")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .header("Authorization", DUMMY_JWT));

    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(MemberRequestFieldsFactory.memberNicknameUpdateRequestFields()),
            responseFields(MemberResponseFieldsFactory.memberNicknameUpdateResponseFields())
        ));
  }

  private NicknameUpdateRequest createTestNicknameUpdateRequest() {
    return new NicknameUpdateRequest("new");
  }

  @DisplayName("멤버_패스워드_업데이트_문서_테스트")
  @Test
  void updatePassword() throws Exception {
    //given
    PasswordUpdateRequest request = createTestPasswordupdateRequest();

    //when
    ResultActions response = mockMvc.perform(patch("/api/me/password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isNoContent())
        .andDo(document.document(
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(MemberRequestFieldsFactory.memberPasswordUpdateRequestFields()),
            responseFields(MemberResponseFieldsFactory.memberPasswordUpdateResponseFields())
        ));
  }

  private PasswordUpdateRequest createTestPasswordupdateRequest() {
    return new PasswordUpdateRequest("new");
  }

  @DisplayName("멤버_로그인_문서_테스트")
  @Test
  void loginTest() throws Exception {
    //given
    LoginRequest request = createTestLoginRequest(testData.getMemberSignupId(),
        testData.getMemberSignupPassword());

    //when
    ResultActions response = mockMvc.perform(post("/api/me/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestFields(MemberRequestFieldsFactory.memberLoginRequestFields()),
            responseFields(MemberResponseFieldsFactory.memberLoginSuccessResponseFields())
        ));
  }

  private LoginRequest createTestLoginRequest(String id, String password) {
    return new LoginRequest(id, password);
  }
}