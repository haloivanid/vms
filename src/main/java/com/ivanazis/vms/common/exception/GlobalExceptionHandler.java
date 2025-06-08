package com.ivanazis.vms.common.exception;

import com.ivanazis.vms.common.ApiResponse;
import com.ivanazis.vms.modules.candidate.exception.CandidateNotFoundException;
import com.ivanazis.vms.modules.candidate.exception.EmailAlreadyExistsException;
import com.ivanazis.vms.modules.vacancy.exception.VacancyNotFoundException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

enum ModuleErrorCode {
  CANDIDATE_EMAIL_EXISTS,
  CANDIDATE_NOT_FOUND,
  VACANCY_NOT_FOUND
}

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ApiResponse.ApiError errorDetails = ApiResponse.ApiError.builder()
        .code("VALIDATION_ERROR")
        .message("Invalid request body. Please check the details.")
        .details(errors)
        .build();

    return new ResponseEntity<>(this.toResponse(errorDetails), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
    ApiResponse.ApiError error = ApiResponse.ApiError.builder()
        .code("INTERNAL_SERVER_ERROR")
        .message("An unexpected error occurred: " + ex.getMessage())
        .build();

    return new ResponseEntity<>(this.toResponse(error), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

    ApiResponse.ApiError error = ApiResponse.ApiError.builder()
        .code(ModuleErrorCode.CANDIDATE_EMAIL_EXISTS.toString())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(this.toResponse(error), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CandidateNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleCandidateNotFound(CandidateNotFoundException ex) {
    ApiResponse.ApiError error = ApiResponse.ApiError.builder()
        .code(ModuleErrorCode.CANDIDATE_NOT_FOUND.toString())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(this.toResponse(error), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(VacancyNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleVacancyNotFound(VacancyNotFoundException ex) {
    ApiResponse.ApiError error = ApiResponse.ApiError.builder().code(ModuleErrorCode.VACANCY_NOT_FOUND.toString())
        .message(ex.getMessage()).build();

    return new ResponseEntity<>(this.toResponse(error), HttpStatus.NOT_FOUND);
  }

  private ApiResponse<Object> toResponse(ApiResponse.ApiError error) {
    return ApiResponse.builder().success(false).error(error).build();
  }
}
