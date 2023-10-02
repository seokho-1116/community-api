package com.example.community.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.CommunityService;
import com.example.community.service.dto.CommunityDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CommunityController.class)
public class CommunityControllerTest extends AbstractRestDocsControllerTest{
  @MockBean
  private CommunityService communityService;

  @DisplayName("커뮤니티_정보_조회_테스트")
  @Test
  void getCommunityTest() throws Exception {
    CommunityDetailDto dto = new CommunityDetailDto("intro", "company",
        "contact", "policy", "terms", "adsinfo");

    Mockito.when(communityService.findCommunity()).thenReturn(dto);

    mockMvc.perform(get("/api/community"))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getCommunityDetailResponseField())
        ));
  }
}
