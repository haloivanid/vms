package com.ivanazis.vms.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse<T> {

  @Builder.Default
  private boolean success = true;

  @Builder.Default
  private String requestId = UUID.randomUUID().toString();

  private T data;
  private PageMeta pageMeta;
  private ApiError error;

  @Data
  @Builder
  public static class PageMeta {
    private int page;
    private int size;
    private long totalData;
  }

  @Data
  @Builder
  public static class ApiError {
    private String code;
    private String message;
    private Map<String, String> details;
  }
}
