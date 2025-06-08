package com.ivanazis.vms.modules.vacancy.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanazis.vms.modules.vacancy.domain.model.RankedCandidate;

public interface RankUseCase {
  Page<RankedCandidate> getRankedCandidatesForVacancy(String vacancyId, Pageable pageable);
}
