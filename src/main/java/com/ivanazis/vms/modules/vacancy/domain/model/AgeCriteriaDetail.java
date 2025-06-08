package com.ivanazis.vms.modules.vacancy.domain.model;

import java.time.LocalDate;
import java.time.Period;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AgeCriteriaDetail extends CriteriaDetail {
  private int min;
  private int max;

  @Override
  public int calculateScore(Candidate candidate, Criteria criteria) {
    if (candidate.getBirthDate() != null) {
      int age = Period.between(candidate.getBirthDate(), LocalDate.now()).getYears();
      return (age >= this.min && age <= this.max) ? criteria.getWeight() : 0;
    }

    return 0;
  }
}