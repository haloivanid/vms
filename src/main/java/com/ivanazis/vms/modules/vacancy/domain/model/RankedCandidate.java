package com.ivanazis.vms.modules.vacancy.domain.model;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedCandidate {
  private Candidate candidate;
  private int score;
}
