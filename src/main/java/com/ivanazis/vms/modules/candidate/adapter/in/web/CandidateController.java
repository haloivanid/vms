package com.ivanazis.vms.modules.candidate.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ivanazis.vms.modules.candidate.adapter.in.web.dto.*;
import com.ivanazis.vms.modules.candidate.domain.model.Candidate;
import com.ivanazis.vms.modules.candidate.domain.port.in.CandidateUseCase;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

  private final CandidateUseCase candidateUseCase;

  @PostMapping
  public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CreateCandidateRequest request) {
    Candidate candidate = toDomain(request);
    Candidate createdCandidate = candidateUseCase.createCandidate(candidate);
    return new ResponseEntity<>(toResponse(createdCandidate), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Page<CandidateResponse>> getAllCandidates(Pageable pageable) {
    Page<Candidate> candidatePage = candidateUseCase.getAllCandidates(pageable);
    Page<CandidateResponse> responsePage = candidatePage.map(this::toResponse);
    return ResponseEntity.ok(responsePage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable String id) {
    Candidate candidate = candidateUseCase.getCandidateById(id);
    return ResponseEntity.ok(toResponse(candidate));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable String id,
      @Valid @RequestBody UpdateCandidateRequest request) {
    Candidate updates = toDomain(request);
    Candidate candidate = candidateUseCase.updateCandidate(id, updates);
    return ResponseEntity.ok(toResponse(candidate));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCandidate(@PathVariable String id) {
    candidateUseCase.deleteCandidate(id);
    return ResponseEntity.noContent().build();
  }

  private Candidate toDomain(CreateCandidateRequest dto) {
    return Candidate.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .birthDate(dto.getBirthDate())
        .gender(dto.getGender())
        .currentSalary(dto.getCurrentSalary())
        .build();
  }

  private Candidate toDomain(UpdateCandidateRequest dto) {
    return Candidate.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .birthDate(dto.getBirthDate())
        .gender(dto.getGender())
        .currentSalary(dto.getCurrentSalary())
        .build();
  }

  private CandidateResponse toResponse(Candidate domain) {
    return CandidateResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .email(domain.getEmail())
        .birthDate(domain.getBirthDate())
        .gender(domain.getGender())
        .currentSalary(domain.getCurrentSalary().longValue())
        .build();
  }
}
