package com.example.community.controller.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.TokenController;
import com.example.community.controller.documentation.fieldsfactory.request.TokenRequestFieldsFactory;
import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.controller.documentation.fieldsfactory.response.TokenResponseFieldsFactory;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = TokenController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class TokenControllerTest extends RestDocsTestSetup {
  @MockBean
  private TokenService tokenService;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("리프레시_토큰_문서_테스트")
  @Test
  void refreshToken() throws Exception {
    //given
    TokenRefreshRequest request = createTestTokenRefreshRequest();

    Mockito.when(tokenService.refresh(any(UUID.class)))
        .thenReturn(createTokenResponseDto());

    //when
    ResultActions response = mockMvc.perform(post("/api/auth/token")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestFields(TokenRequestFieldsFactory.tokenRefreshRequestFields()),
            responseFields(TokenResponseFieldsFactory.tokenRefreshResponseField())
        ));
  }

  private TokenResponseDto createTokenResponseDto() {
    return new TokenResponseDto("", UUID.randomUUID(), "");
  }

  private TokenRefreshRequest createTestTokenRefreshRequest() {
    return new TokenRefreshRequest(UUID.randomUUID());
  }
}