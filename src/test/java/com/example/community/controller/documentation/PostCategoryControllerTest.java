package com.example.community.controller.documentation;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.PostCategoryController;
import com.example.community.controller.documentation.fieldsfactory.PostCategoryFieldsFactory;
import com.example.community.service.PostCategoryService;
import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = PostCategoryController.class)
class PostCategoryControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private PostCategoryService postCategoryService;

  @Test
  void getPostCategoriesById() throws Exception {
    UUID boardPublicId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");

    Mockito.when(postCategoryService.findPostCategoryById(boardPublicId))
        .thenReturn(createTestPostCategoryDto());

    mockMvc.perform(get("/api/boards/{board_id}/posts/categories", boardPublicId))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(PostCategoryFieldsFactory.getPostCategoryResponseField()),
            pathParameters(parameterWithName("board_id").description("게시판 id"))
        ));
  }

  private List<PostCategoryDto> createTestPostCategoryDto() {
    return IntStream.range(0, 10)
        .mapToObj(num -> new PostCategoryDto("name", "description"))
        .collect(Collectors.toList());
  }
}