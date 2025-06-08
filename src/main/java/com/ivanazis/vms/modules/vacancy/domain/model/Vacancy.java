package com.ivanazis.vms.modules.vacancy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
  private String id;
  private String name;

  private List<Criteria> criteria;

  public int getCandidateTotalScore(Candidate candidate) {
    return this.criteria.stream().mapToInt(criteria -> criteria.getDetails().calculateScore(candidate, criteria))
        .sum();
  }
}