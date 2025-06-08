package com.ivanazis.vms.modules.vacancy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SalaryCriteriaDetail extends CriteriaDetail {
  private BigDecimal min;
  private BigDecimal max;

  @Override
  public int calculateScore(Candidate candidate, Criteria criteria) {
    if (candidate.getCurrentSalary() != null) {
      boolean isWithinBudget = candidate.getCurrentSalary().compareTo(this.max) <= 0;
      return isWithinBudget ? criteria.getWeight() : 0;
    }
    return 0;
  }
}