package com.ivanazis.vms.modules.vacancy.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class CreateVacancyRequest {
  @NotBlank
  private String name;

  @NotEmpty
  @Valid
  private List<CriteriaRequest> criteria;
}