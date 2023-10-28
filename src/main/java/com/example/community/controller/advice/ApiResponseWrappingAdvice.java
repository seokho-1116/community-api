package com.example.community.controller.advice;

import com.example.community.controller.response.ApiResponse;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.example.community.controller"})
public class ApiResponseWrappingAdvice implements ResponseBodyAdvice<Object> {
  @Override
  public boolean supports(@NonNull MethodParameter returnType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return returnType.hasMethodAnnotation(RequestMapping.class);
  }

  @Override
  public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
    if (body == null) {
      body = "";
    }

    return ApiResponse.success(body);
  }
}
