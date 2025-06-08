package com.ivanazis.vms.modules.vacancy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ivanazis.vms.common.ApiResponse;

enum VacancyErrorCode {
  VACANCY_NOT_FOUND,
}

@ControllerAdvice
public class VacancyExceptionHandler {
  @ExceptionHandler(VacancyNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleVacancyNotFound(VacancyNotFoundException ex) {
    ApiResponse.ApiError error = ApiResponse.ApiError.builder().code(VacancyErrorCode.VACANCY_NOT_FOUND.toString())
        .message(ex.getMessage()).build();

    return new ResponseEntity<>(this.toResponse(error), HttpStatus.NOT_FOUND);
  }

  private ApiResponse<Object> toResponse(ApiResponse.ApiError error) {
    return ApiResponse.builder().success(false).error(error).build();
  }
}
