package com.ivanazis.vms.modules.candidate.adapter.out.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ivanazis.vms.modules.candidate.domain.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "candidates")
public class CandidateDocument {

  @Id
  private String id;

  private String name;

  @Indexed(unique = true)
  private String email;
  private LocalDate birthDate;
  private Gender gender;
  private BigDecimal currentSalary;
}
