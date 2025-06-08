package com.ivanazis.vms.modules.vacancy.domain.model;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;
import com.ivanazis.vms.modules.candidate.domain.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenderCriteriaDetail extends CriteriaDetail {
  private Gender expected;

  @Override
  public int calculateScore(Candidate candidate, Criteria criteria) {
    if (candidate.getBirthDate() != null) {
      if (this.expected == Gender.ANY || this.expected.equals(candidate.getGender())) {
        return criteria.getWeight();
      }
    }

    return 0;
  }
}