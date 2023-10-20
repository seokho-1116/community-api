package com.example.community.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.request.EmailUpdateRequest;
import com.example.community.controller.request.NicknameUpdateRequest;
import com.example.community.controller.request.PasswordUpdateRequest;
import com.example.community.controller.request.SignupRequest;
import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.service.MemberService;
import com.example.community.service.dto.MemberDetailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MemberController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class MemberControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private MemberService memberService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void signup() throws Exception {
    SignupRequest request = new SignupRequest("id", "nickname", "password",
        "email");
    UUID publicId = UUID.randomUUID();

    when(memberService.createMember(any())).thenReturn(publicId);

    mockMvc.perform(post("/api/me/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getMemberCreateResponseField())
        ));
  }

  @Test
  void getMember() throws Exception {
    String jwt = "Bearer token";

    when(memberService.findMemberByPublicId(any())).thenReturn(createTestMemberDetailDto());

    mockMvc.perform(get("/api/me")
            .header("Authorization", jwt))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getMemberDetailResponseField()),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰"))
        ));
  }

  private MemberDetailDto createTestMemberDetailDto() {
    return new MemberDetailDto("signupId",
        "nickname", "email", OffsetDateTime.now());
  }

  @Test
  void updateEmail() throws Exception {
    String jwt = "Bearer token";
    EmailUpdateRequest request = new EmailUpdateRequest("new");

    when(memberService.updateEmail(any(), any())).thenReturn(request.getEmail());

    mockMvc.perform(patch("/api/me/email")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", jwt))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getMemberEmailUpdateResponseField()),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰"))
        ));
  }

  @Test
  void updateNickname() throws Exception {
    String jwt = "Bearer token";
    NicknameUpdateRequest request = new NicknameUpdateRequest("new");

    when(memberService.updateNickname(any(), any())).thenReturn(request.getNickname());

    mockMvc.perform(patch("/api/me/nickname")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", jwt))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getMemberNicknameUpdateResponseField()),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰"))
        ));
  }

  @Test
  void updatePassword() throws Exception {
    String jwt = "Bearer token";
    PasswordUpdateRequest request = new PasswordUpdateRequest("new");

    mockMvc.perform(patch("/api/me/password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", jwt))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getMemberPasswordUpdateResponseField()),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰"))
        ));
  }

  @Test
  void loginSuccessTest() throws Exception {
    LoginRequest request = new LoginRequest("id", "password");

    mockMvc.perform(post("/api/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getLoginSuccessResponseField()),
            requestFields(fieldWithPath("signupId").description("로그인 id"),
                fieldWithPath("signupPassword").description("로그인 비밀번호"))
        ));
  }

  @Test
  void loginFailureTest() throws Exception {
    LoginRequest request = new LoginRequest("id", "none");

    mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnauthorized());
  }
}