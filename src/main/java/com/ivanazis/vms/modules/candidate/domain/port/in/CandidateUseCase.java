package com.ivanazis.vms.modules.candidate.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

/**
 * Port (kontrak) untuk semua use case yang berhubungan dengan Candidate.
 */
public interface CandidateUseCase {
  Candidate createCandidate(Candidate candidate);

  Page<Candidate> getAllCandidates(Pageable pageable);

  Candidate getCandidateById(String id);

  Candidate updateCandidate(String id, Candidate candidate);

  void deleteCandidate(String id);
}
