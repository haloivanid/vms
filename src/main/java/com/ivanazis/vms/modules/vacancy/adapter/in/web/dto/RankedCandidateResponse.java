package com.ivanazis.vms.modules.vacancy.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankedCandidateResponse {
  private String id;
  private String name;
  private String email;
  private int score;
}
