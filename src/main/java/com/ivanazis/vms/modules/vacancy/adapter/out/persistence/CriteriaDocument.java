package com.ivanazis.vms.modules.vacancy.adapter.out.persistence;

import com.ivanazis.vms.modules.vacancy.domain.model.CriteriaDetail;
import lombok.Data;

@Data
public class CriteriaDocument {
  private String name;
  private int weight;

  private CriteriaDetail details;
}