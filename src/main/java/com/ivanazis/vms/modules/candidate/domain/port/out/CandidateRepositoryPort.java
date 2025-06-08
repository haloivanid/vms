package com.ivanazis.vms.modules.candidate.domain.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

/**
 * Port (kontrak) untuk operasi persistence Candidate.
 * Lapisan domain bergantung pada interface ini, bukan pada implementasi
 * database.
 */
public interface CandidateRepositoryPort {

  Candidate save(Candidate candidate);

  Page<Candidate> findAll(Pageable pageable);

  Optional<Candidate> findById(String id);

  Optional<Candidate> findByEmail(String email);

  void deleteById(String id);
}
