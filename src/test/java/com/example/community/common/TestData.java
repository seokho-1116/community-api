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
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class TestData {
  private static final Logger logger = LoggerFactory.getLogger(TestData.class);

  private static final String BOARD_DATA_JSON_PATH = "data/board_data.json";
  private static final String POST_DATA_JSON_PATH = "data/post_data.json";
  private static final String POST_CATEGORY_DATA_JSON_PATH = "data/post_category_data.json";
  private static final String MEMBER_DATA_JSON_PATH = "data/member_data.json";
  private static final String COMMENT_DATA_JSON_PATH = "data/comment_data.json";
  private static final String TOKEN_DATA_PATH = "data/token_data.json";

  private final ObjectMapper objectMapper = initObjectMapper();
  private final TestBoard testBoard;
  private final List<TestPost> testPostList;
  private final TestMember testMember;
  private final List<TestComment> testCommentList;
  private final TestToken testToken;

  public TestData() {
    testBoard = loadJsonData(BOARD_DATA_JSON_PATH, new TypeReference<>() {});

    testPostList = loadJsonData(POST_DATA_JSON_PATH, new TypeReference<>() {});

    testMember = loadJsonData(MEMBER_DATA_JSON_PATH, new TypeReference<>() {});

    testCommentList = loadJsonData(COMMENT_DATA_JSON_PATH, new TypeReference<>() {});

    testToken = loadJsonData(TOKEN_DATA_PATH, new TypeReference<>() {});
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

  public String getPostPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testPostList.get(testDataType.index).publicId);
  }

  public String getPostBoardPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testPostList.get(testDataType.index).boardPublicId);
  }

  public String getBoardPublicId() {
    return Objects.requireNonNull(testBoard.publicId);
  }

  public String getMemberPublicId() {
    return Objects.requireNonNull(testMember.memberPublicId);
  }

  public String getMemberSignupId() {
    return Objects.requireNonNull(testMember.signupId);
  }

  public String getMemberSignupPassword() {
    return Objects.requireNonNull(testMember.signupPassword);
  }

  public String getCommentBoardPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testCommentList.get(testDataType.index).boardPublicId);
  }

  public String getCommentPostPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testCommentList.get(testDataType.index).postPublicId);
  }

  public String getCommentMemberPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testCommentList.get(testDataType.index).memberPublicId);
  }

  public String getCommentPublicId(TestDataType testDataType) {
    return Objects.requireNonNull(testCommentList.get(testDataType.index).publicId);
  }

  public String getTokenPublicId() {
    return Objects.requireNonNull(testToken.publicId);
  }

  public String getTokenMemberPublicId() {
    return Objects.requireNonNull(testToken.memberPublicId);
  }

  public UUID getNullUUID() {
    return UUID.fromString("00000000-0000-0000-0000-000000000000");
  }

  public String getTokenRefreshToken() {
    return Objects.requireNonNull(testToken.refreshToken);
  }

  public enum TestDataType {
    FOR_DELETE(2),
    FOR_UPDATE(1),
    MY(3),
    COMMON(0);

    private final int index;

    TestDataType(int index) {
      this.index = index;
    }
  }

  private static class TestBoard {
    private final String publicId;

    @JsonCreator
    private TestBoard(@JsonProperty("public_id") String publicId) {
      this.publicId = publicId;
    }
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

  private static class TestComment {
    private final String boardPublicId;
    private final String postPublicId;
    private final String memberPublicId;
    private final String publicId;

    @JsonCreator
    public TestComment(@JsonProperty("board_public_id") String boardPublicId,
        @JsonProperty("post_public_id") String postPublicId,
        @JsonProperty("member_public_id") String memberPublicId,
        @JsonProperty("public_id") String publicId)
    {
      this.boardPublicId = boardPublicId;
      this.postPublicId = postPublicId;
      this.memberPublicId = memberPublicId;
      this.publicId = publicId;
    }
  }

  private static class TestToken {
    private final String publicId;
    private final String memberPublicId;
    private final String refreshToken;

    @JsonCreator
    public TestToken(@JsonProperty("public_id") String publicId,
        @JsonProperty("member_public_id") String memberPublicId,
        @JsonProperty("refresh_token") String refreshToken)
    {
      this.publicId = publicId;
      this.memberPublicId = memberPublicId;
      this.refreshToken = refreshToken;
    }
  }
}
