package com.example.community.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class TestData {
  private static final Logger logger = LoggerFactory.getLogger(TestData.class);
  private static final String BOARD_DATA_JSON_PATH = "data/board_data.json";
  private static final String POST_DATA_JSON_PATH = "data/post_data.json";
  private static final String POST_CATEGORY_DATA_JSON_PATH = "data/post_category_data.json";
  private static final String MEMBER_DATA_JSON_PATH = "data/member_data.json";
  private final ObjectMapper objectMapper = initObjectMapper();

  private final TestPost myPost;
  private final TestPost deletePost;
  private final TestPost updatePost;
  private final TestPost post;
  private final String boardPublicId;
  private final String postCategoryPublicId;
  private final TestMember member;

  public TestData() {
    boardPublicId = loadJsonData(BOARD_DATA_JSON_PATH,
        new TypeReference<Map<String, String>>() {})
        .get("public_id");
    List<TestPost> posts = loadJsonData(POST_DATA_JSON_PATH, new TypeReference<>() {});
    post = posts.get(0);
    deletePost = posts.get(1);
    updatePost = posts.get(2);
    myPost = posts.get(3);
    postCategoryPublicId = loadJsonData(POST_CATEGORY_DATA_JSON_PATH,
        new TypeReference<Map<String, String>>() {})
        .get("public_id");
    member = loadJsonData(MEMBER_DATA_JSON_PATH, new TypeReference<>() {});
  }

  private ObjectMapper initObjectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  private <T> T loadJsonData(String path, TypeReference<T> typeReference) {
    try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
      return objectMapper.readValue(inputStream, typeReference);
    } catch (IOException exception) {
      logger.error("Error reading data from {}", path, exception);
      throw new RuntimeException("Failed to load test data from " + path, exception);
    }
  }

  public String getMyPostPublicId() {
    return myPost.publicId;
  }

  public String getMyPostBoardPublicId() {
    return myPost.boardPublicId;
  }

  public String getDeletePostPublicId() {
    return deletePost.publicId;
  }

  public String getDeletePostBoardPublicId() {
    return deletePost.boardPublicId;
  }

  public String getUpdatePostPublicId() {
    return updatePost.publicId;
  }

  public String getUpdatePostBoardPublicId() {
    return updatePost.boardPublicId;
  }

  public String getPostPublicId() {
    return post.publicId;
  }

  public String getPostBoardPublicId() {
    return post.boardPublicId;
  }

  public String getBoardPublicId() {
    return boardPublicId;
  }

  public String getPostCategoryPublicId() {
    return postCategoryPublicId;
  }

  public String getMemberPublicId() {
    return member.memberPublicId;
  }

  public String getMemberSignupId() {
    return member.signupId;
  }

  public String getMemberSignupPassword() {
    return member.signupPassword;
  }

  private static class TestPost {
    private final String boardPublicId;
    private final String publicId;

    @JsonCreator
    private TestPost(@JsonProperty("board_public_id") String boardPublicId, @JsonProperty("public_id") String publicId) {
      this.boardPublicId = boardPublicId;
      this.publicId = publicId;
    }
  }

  private static class TestMember {
    private final String memberPublicId;
    private final String signupId;
    private final String signupPassword;

    @JsonCreator
    private TestMember(@JsonProperty("public_id") String memberPublicId,
        @JsonProperty("signup_id") String signupId,
        @JsonProperty("signup_password") String signupPassword)
    {
      this.memberPublicId = memberPublicId;
      this.signupId = signupId;
      this.signupPassword = signupPassword;
    }
  }
}
