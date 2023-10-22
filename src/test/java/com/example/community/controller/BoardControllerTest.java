package com.example.community.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.documentation.fieldsfactory.BoardFieldsFactory;
import com.example.community.service.BoardService;
import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
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


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private BoardService boardService;

  @Test
  void getAllBoards() throws Exception {
    Mockito.when(boardService.findAllBoards()).thenReturn(createTestBoards());

    mockMvc.perform(get("/api/boards"))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(BoardFieldsFactory.getBoardSummaryResponseFields())
        ));
  }

  @Test
  void getBoardById() throws Exception {
    UUID boardPublicId = UUID.randomUUID();

    Mockito.when(boardService.findBoardByPublicId(boardPublicId))
        .thenReturn(createTestBoardDetail());

    mockMvc.perform(get("/api/boards/{board_id}", boardPublicId))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(BoardFieldsFactory.getBoardDetailResponseFields()),
            pathParameters(parameterWithName("board_id").description("게시판 id"))
        ));
  }

  private static List<BoardSummaryResponseDto> createTestBoards() {
    return IntStream.range(0, 10)
        .mapToObj(BoardControllerTest::createTestBoardSummary)
        .collect(Collectors.toList());
  }

  private static BoardSummaryResponseDto createTestBoardSummary(int number) {
    return new BoardSummaryResponseDto(UUID.randomUUID().toString(), String.valueOf(number));
  }

  private static BoardDetailResponseDto createTestBoardDetail() {
    return new BoardDetailResponseDto(UUID.randomUUID().toString(), "name",
        "description", OffsetDateTime.now());
  }
}