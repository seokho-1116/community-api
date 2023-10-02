package com.example.community.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.BoardService;
import com.example.community.service.dto.BoardDetailDto;
import com.example.community.service.dto.BoardSummaryDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private BoardService boardService;

  @Test
  void getAllBoards() throws Exception {
    Mockito.when(boardService.findAllBoards()).thenReturn(createBoards());

    mockMvc.perform(get("/api/boards"))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getBoardSummaryResponseFields())
        ));
  }

  @Test
  void getBoardById() throws Exception {
    String boardId = UUID.randomUUID().toString();
    Mockito.when(boardService.findBoardById(boardId)).thenReturn(createBoardDetail());

    mockMvc.perform(get("/api/boards/{board_id}", boardId))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getBoardDetailResponseFields()),
            pathParameters(parameterWithName("board_id").description("게시판 id"))
        ));
  }

  private static List<BoardSummaryDto> createBoards() {
    return IntStream.range(0, 10)
        .mapToObj(BoardControllerTest::createBoardSummary)
        .collect(Collectors.toList());
  }

  private static BoardSummaryDto createBoardSummary(int number) {
    return new BoardSummaryDto(UUID.randomUUID().toString(), String.valueOf(number));
  }

  private static BoardDetailDto createBoardDetail() {
    return new BoardDetailDto(UUID.randomUUID().toString(), "name", "description",
        OffsetDateTime.now());
  }
}