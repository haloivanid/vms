package com.ivanazis.vms.modules.candidate.adapter.in.web.dto;

import java.time.LocalDate;

import com.ivanazis.vms.modules.candidate.domain.model.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateResponse {
  private String id;
  private String name;
  private String email;
  private LocalDate birthDate;
  private Gender gender;
  private Long currentSalary;
}