package com.example.community.controller.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.CommunityController;
import com.example.community.controller.documentation.fieldsfactory.response.CommunityReponseFieldsFactory;
import com.example.community.service.CommunityService;
import com.example.community.service.dto.CommunityDetailResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = CommunityController.class)
class CommunityControllerTest extends RestDocsTestSetup {
  @MockBean
  private CommunityService communityService;

  @DisplayName("커뮤니티_정보_조회_테스트")
  @Test
  void getCommunityTest() throws Exception {
    //given
    CommunityDetailResponseDto dto = createTestCommunityDetailDto();

    Mockito.when(communityService.findCommunity()).thenReturn(dto);

    //when
    ResultActions response = mockMvc.perform(get("/api/community"));

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(CommunityReponseFieldsFactory.communityDetailResponseFields())
        ));
  }

  private CommunityDetailResponseDto createTestCommunityDetailDto() {
    return new CommunityDetailResponseDto("intro", "company",
        "contact", "policy", "terms", "adsinfo");
  }
}
