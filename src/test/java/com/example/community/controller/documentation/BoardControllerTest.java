package com.example.community.controller.documentation;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.BoardController;
import com.example.community.controller.documentation.fieldsfactory.response.BoardResponseFieldsFactory;
import com.example.community.service.BoardService;
import com.example.community.service.dto.BoardDetailResponseDto;
import com.example.community.service.dto.BoardSummaryResponseDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest extends RestDocsTestSetup {
  @MockBean
  private BoardService boardService;

  @DisplayName("모든_게시판_조회_문서_테스트")
  @Test
  void getAllBoards() throws Exception {
    //given
    Mockito.when(boardService.findAllBoards()).thenReturn(createTestBoards());

    //when
    ResultActions response = mockMvc.perform(get("/api/boards"));

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(BoardResponseFieldsFactory.boardSummaryResponseFields())
        ));
  }

  @DisplayName("특정_게시판_조회_문서_테스트")
  @Test
  void getBoardById() throws Exception {
    //given
    UUID boardPublicId = UUID.randomUUID();

    Mockito.when(boardService.findBoardByPublicId(boardPublicId))
        .thenReturn(createTestBoardDetail());

    //then
    ResultActions response = mockMvc.perform(get("/api/boards/{board_id}",
        boardPublicId));

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(parameterWithName("board_id").description("게시판 id")),
            responseFields(BoardResponseFieldsFactory.boardDetailResponseFields())
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