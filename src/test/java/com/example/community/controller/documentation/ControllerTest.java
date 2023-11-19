package com.example.community.controller.documentation;

import com.example.community.common.TestData;
import com.example.community.controller.documentation.config.SecurityTestConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Import(SecurityTestConfiguration.class)
abstract class ControllerTest {
  protected final TestData testData = new TestData();
  protected MockMvc mockMvc;
}
