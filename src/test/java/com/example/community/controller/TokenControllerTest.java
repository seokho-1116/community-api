package com.example.community.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenRefreshResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TokenController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class TokenControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private TokenService tokenService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void refreshToken() throws Exception {
    TokenRefreshRequest request = createTestTokenRefreshRequest();

    Mockito.when(tokenService.refresh(request.getRefreshTokenPublicId()))
        .thenReturn(createTokenRefreshResponseDto());

    mockMvc.perform(post("/api/auth/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getTokenRefreshResponseField())
        ));
  }

  private static TokenRefreshRequest createTestTokenRefreshRequest() {
    return new TokenRefreshRequest(UUID.randomUUID());
  }

  private TokenRefreshResponseDto createTokenRefreshResponseDto() {
    return TokenRefreshResponseDto.create("access", "3600");
  }
}