package com.ivanazis.vms.modules.vacancy.adapter.in.web.dto;

import com.ivanazis.vms.modules.vacancy.domain.model.CriteriaDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriteriaRequest {
  @NotBlank
  private String name;

  @Min(1)
  private int weight;

  @NotNull
  @Valid
  private CriteriaDetail details;
}