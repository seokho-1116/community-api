package com.example.community.service;

import com.example.community.common.TestData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
abstract class ServiceTest {
  static final TestData TEST_DATA = new TestData();
}
