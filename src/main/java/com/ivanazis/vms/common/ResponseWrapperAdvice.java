package com.ivanazis.vms.common;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.ivanazis.vms")
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(@SuppressWarnings("null") MethodParameter returnType,
      @SuppressWarnings("null") Class<? extends HttpMessageConverter<?>> converterType) {
    return !returnType.getParameterType().equals(ApiResponse.class);
  }

  @Override
  public Object beforeBodyWrite(@SuppressWarnings("null") Object body,
      @SuppressWarnings("null") MethodParameter returnType, @SuppressWarnings("null") MediaType selectedContentType,
      @SuppressWarnings("null") Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @SuppressWarnings("null") ServerHttpRequest request, @SuppressWarnings("null") ServerHttpResponse response) {

    if (body instanceof ApiResponse) {
      return body;
    }

    if (body instanceof Page) {
      Page<?> page = (Page<?>) body;

      ApiResponse.PageMeta pageMeta = ApiResponse.PageMeta.builder()
          .page(page.getNumber())
          .size(page.getSize())
          .totalData(page.getTotalElements())
          .build();

      return ApiResponse.builder()
          .data(page.getContent())
          .pageMeta(pageMeta)
          .build();
    }

    return ApiResponse.builder().data(body).build();
  }
}
