package com.ivanazis.vms.modules.vacancy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
  private String name;
  private int weight;

  private CriteriaDetail details;
}