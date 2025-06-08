package com.ivanazis.vms.modules.candidate.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
  private String id;
  private String name;
  private String email;
  private LocalDate birthDate;
  private Gender gender;
  private BigDecimal currentSalary;
}
