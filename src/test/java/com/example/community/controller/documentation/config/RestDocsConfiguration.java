package com.example.community.controller.documentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public class RestDocsConfiguration {
  @Bean
  public RestDocumentationResultHandler write(){
    return MockMvcRestDocumentation.document(
        "{class-name}/{method-name}",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
    );
  }
}
