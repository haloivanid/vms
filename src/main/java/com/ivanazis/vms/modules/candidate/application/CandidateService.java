package com.ivanazis.vms.modules.candidate.application;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;
import com.ivanazis.vms.modules.candidate.domain.port.in.CandidateUseCase;
import com.ivanazis.vms.modules.candidate.domain.port.out.CandidateRepositoryPort;
import com.ivanazis.vms.modules.candidate.exception.CandidateNotFoundException;
import com.ivanazis.vms.modules.candidate.exception.EmailAlreadyExistsException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService implements CandidateUseCase {

  private final CandidateRepositoryPort candidateRepository;

  @Override
  public Candidate createCandidate(Candidate candidate) {
    candidateRepository.findByEmail(candidate.getEmail())
        .ifPresent(existingCandidate -> {
          throw new EmailAlreadyExistsException("Email " + candidate.getEmail() + " is already in use.");
        });
    return candidateRepository.save(candidate);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Candidate> getAllCandidates(Pageable pageable) {
    return candidateRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Candidate getCandidateById(String id) {
    return candidateRepository.findById(id)
        .orElseThrow(() -> new CandidateNotFoundException("candidate not found with id:" + id));
  }

  @Override
  public Candidate updateCandidate(String id, Candidate candidateUpdates) {
    Candidate candidate = candidateRepository.findById(id)
        .orElseThrow(() -> new CandidateNotFoundException("candidate not found with id:" + id));

    candidate.setName(candidateUpdates.getName());
    candidate.setEmail(candidateUpdates.getEmail());
    candidate.setBirthDate(candidateUpdates.getBirthDate());
    candidate.setGender(candidateUpdates.getGender());
    candidate.setCurrentSalary(candidateUpdates.getCurrentSalary());

    candidateRepository.save(candidate);

    return candidate;
  }

  @Override
  public void deleteCandidate(String id) {
    candidateRepository.deleteById(id);
  }
}
