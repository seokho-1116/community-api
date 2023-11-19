package com.example.community.controller.documentation;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.community.common.TestData;
import com.example.community.controller.documentation.config.ControllerTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
@AutoConfigureRestDocs
@Import(ControllerTestConfiguration.class)
abstract class AbstractRestDocsControllerTest {
  protected final TestData testData = new TestData();
  protected MockMvc mockMvc;

  @Autowired
  protected RestDocumentationResultHandler document;

  @BeforeEach
  void beforeEach(WebApplicationContext context, RestDocumentationContextProvider provider) {
    this.mockMvc = getRestDockMockMvc(context, provider, document);
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
