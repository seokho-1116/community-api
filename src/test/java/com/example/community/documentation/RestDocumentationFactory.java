package com.example.community.documentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class RestDocumentationFactory {
  public static RestDocumentationResultHandler getRestDocumentation() {
    return MockMvcRestDocumentation.document(
        "{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint())
    );
  }
}
