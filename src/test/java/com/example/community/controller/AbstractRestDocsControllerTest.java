package com.example.community.controller;

import com.example.community.config.ControllerTestConfiguration;
import com.example.community.documentation.MockMVCFactory;
import com.example.community.documentation.RestDocumentationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@Import(ControllerTestConfiguration.class)
class AbstractRestDocsControllerTest {
  protected MockMvc mockMvc;
  protected RestDocumentationResultHandler document;

  @BeforeEach
  void beforeEach(WebApplicationContext context, RestDocumentationContextProvider provider) {
    this.document = RestDocumentationFactory.getRestDocumentation();
    this.mockMvc = MockMVCFactory.getRestDockMockMvc(context, provider, document);
  }
}
