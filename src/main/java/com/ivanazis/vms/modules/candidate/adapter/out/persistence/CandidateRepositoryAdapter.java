package com.ivanazis.vms.modules.candidate.adapter.out.persistence;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;
import com.ivanazis.vms.modules.candidate.domain.port.out.CandidateRepositoryPort;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CandidateRepositoryAdapter implements CandidateRepositoryPort {

  private final CandidateMongoRepository mongoRepository;

  @Override
  public Candidate save(Candidate candidate) {
    CandidateDocument document = toDocument(candidate);
    CandidateDocument savedDocument = mongoRepository.save(document);
    return toDomain(savedDocument);
  }

  @Override
  public Page<Candidate> findAll(Pageable pageable) {
    Page<CandidateDocument> documentPage = mongoRepository.findAll(pageable);
    return documentPage.map(this::toDomain);
  }

  @Override
  public Optional<Candidate> findById(String id) {
    return mongoRepository.findById(id).map(this::toDomain);
  }

  @Override
  public Optional<Candidate> findByEmail(String email) {
    return mongoRepository.findByEmail(email).map(this::toDomain);
  }

  @Override
  public void deleteById(String id) {
    mongoRepository.deleteById(id);
  }

  private CandidateDocument toDocument(Candidate candidate) {
    return CandidateDocument.builder()
        .id(candidate.getId())
        .name(candidate.getName())
        .email(candidate.getEmail())
        .birthDate(candidate.getBirthDate())
        .gender(candidate.getGender())
        .currentSalary(candidate.getCurrentSalary())
        .build();
  }

  private Candidate toDomain(CandidateDocument document) {
    return Candidate.builder()
        .id(document.getId())
        .name(document.getName())
        .email(document.getEmail())
        .birthDate(document.getBirthDate())
        .gender(document.getGender())
        .currentSalary(document.getCurrentSalary())
        .build();
  }
}
