package com.ivanazis.vms.modules.candidate.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivanazis.vms.modules.candidate.domain.model.Gender;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCandidateRequest {
  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotNull
  @Past
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotNull
  private Gender gender;

  @NotNull
  @PositiveOrZero
  private BigDecimal currentSalary;
}