package com.example.community.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostSummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class PostControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getPosts() throws Exception {
    long total = 50L;

    PagePostRequest request = new PagePostRequest(OffsetDateTime.now().toString(), 10);

    Mockito.when(postService.findPosts(anyString(), anyInt()))
        .thenReturn(createTestPage(request.getSize(), total));

    mockMvc.perform(get("/api/boards/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getPagePostsResponseField())
        ));
  }

  private Page<PostSummaryDto> createTestPage(int size, long total) {
    List<PostSummaryDto> content = IntStream.range(0, size)
        .mapToObj(PostControllerTest::createTestPostSummaryDto)
        .collect(Collectors.toList());

    return new PageImpl<>(content, Pageable.ofSize(size), total);
  }

  private static PostSummaryDto createTestPostSummaryDto(int number) {
    return new PostSummaryDto(UUID.randomUUID().toString(), "title", "content",
        "user", number, "유머", "인기", OffsetDateTime.now());
  }
}