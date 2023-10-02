package com.example.community.documentation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

public class MockMVCFactory {
  public static MockMvc getRestDockMockMvc(WebApplicationContext context,
      RestDocumentationContextProvider provider, RestDocumentationResultHandler document) {
    return MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
        .alwaysDo(document)
        .alwaysDo(print())
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }
}
