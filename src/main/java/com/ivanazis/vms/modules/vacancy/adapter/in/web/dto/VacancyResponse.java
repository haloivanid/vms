package com.ivanazis.vms.modules.vacancy.adapter.in.web.dto;

import com.ivanazis.vms.modules.vacancy.domain.model.Criteria;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class VacancyResponse {
  private String id;
  private String name;
  private List<Criteria> criteria;
}
