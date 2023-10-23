package com.example.community.controller.documentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.community.config.ControllerTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
@AutoConfigureRestDocs
@Import(ControllerTestConfiguration.class)
class AbstractRestDocsControllerTest {
  protected MockMvc mockMvc;
  protected RestDocumentationResultHandler document;

  @BeforeEach
  void beforeEach(WebApplicationContext context, RestDocumentationContextProvider provider) {
    this.document = getRestDocumentation();
    this.mockMvc = getRestDockMockMvc(context, provider, document);
  }

  private RestDocumentationResultHandler getRestDocumentation() {
    return MockMvcRestDocumentation.document(
        "{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint())
    );
  }

  private MockMvc getRestDockMockMvc(WebApplicationContext context,
      RestDocumentationContextProvider provider, RestDocumentationResultHandler document) {
    return MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
        .apply(springSecurity())
        .alwaysDo(document)
        .alwaysDo(print())
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }
}
