package com.ivanazis.vms.modules.vacancy.adapter.in.web;

import com.ivanazis.vms.modules.vacancy.adapter.in.web.dto.CreateVacancyRequest;
import com.ivanazis.vms.modules.vacancy.adapter.in.web.dto.CriteriaRequest;
import com.ivanazis.vms.modules.vacancy.adapter.in.web.dto.RankedCandidateResponse;
import com.ivanazis.vms.modules.vacancy.adapter.in.web.dto.UpdateVacancyRequest;
import com.ivanazis.vms.modules.vacancy.adapter.in.web.dto.VacancyResponse;
import com.ivanazis.vms.modules.vacancy.domain.model.Criteria;
import com.ivanazis.vms.modules.vacancy.domain.model.RankedCandidate;
import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import com.ivanazis.vms.modules.vacancy.domain.port.in.RankUseCase;
import com.ivanazis.vms.modules.vacancy.domain.port.in.VacancyUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vacancies")
@RequiredArgsConstructor
public class VacancyController {

  private final VacancyUseCase vacancyUseCase;
  private final RankUseCase rankUseCase;

  @PostMapping
  public ResponseEntity<VacancyResponse> createVacancy(@Valid @RequestBody CreateVacancyRequest request) {
    Vacancy vacancy = toDomain(request);
    Vacancy createdVacancy = vacancyUseCase.createVacancy(vacancy);
    return new ResponseEntity<>(vacancyToReponse(createdVacancy), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Page<VacancyResponse>> getAllVacancies(Pageable pageable) {
    Page<Vacancy> vacancyPage = vacancyUseCase.getAllVacancies(pageable);
    return ResponseEntity.ok(vacancyPage.map(this::vacancyToReponse));
  }

  @GetMapping("/{id}")
  public ResponseEntity<VacancyResponse> getVacancyById(@PathVariable String id) {
    return ResponseEntity.ok(this.vacancyToReponse(vacancyUseCase.getVacancyById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<VacancyResponse> updateVacancy(@PathVariable String id,
      @Valid @RequestBody UpdateVacancyRequest request) {
    Vacancy vacancyUpdates = toDomain(request);
    Vacancy updatedVacancy = vacancyUseCase.updateVacancy(id, vacancyUpdates);
    return ResponseEntity.ok(vacancyToReponse(updatedVacancy));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVacancy(@PathVariable String id) {
    vacancyUseCase.deleteVacancy(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/ranks")
  public ResponseEntity<Page<RankedCandidateResponse>> getVacancyRankCandidates(@PathVariable String id,
      Pageable pageable) {
    Page<RankedCandidate> rankedCandidatePage = rankUseCase.getRankedCandidatesForVacancy(id, pageable);
    return ResponseEntity.ok(rankedCandidatePage.map(this::rankedCandidateToResponse));
  }

  private Vacancy toDomain(CreateVacancyRequest dto) {
    return Vacancy.builder()
        .name(dto.getName())
        .criteria(dto.getCriteria().stream().map(c -> Criteria.builder()
            .name(c.getName())
            .weight(c.getWeight())
            .details(c.getDetails())
            .build()).collect(Collectors.toList()))
        .build();
  }

  private Vacancy toDomain(UpdateVacancyRequest dto) {
    return Vacancy.builder()
        .name(dto.getName())
        .criteria(dto.getCriteria().stream().map(this::toCriteriaDomain).collect(Collectors.toList()))
        .build();
  }

  private Criteria toCriteriaDomain(CriteriaRequest criteriaRequest) {
    return Criteria.builder()
        .name(criteriaRequest.getName())
        .weight(criteriaRequest.getWeight())
        .details(criteriaRequest.getDetails())
        .build();
  }

  private VacancyResponse vacancyToReponse(Vacancy domain) {
    return VacancyResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .criteria(domain.getCriteria())
        .build();
  }

  private RankedCandidateResponse rankedCandidateToResponse(RankedCandidate domain) {
    return RankedCandidateResponse.builder().id(domain.getCandidate().getId()).name(domain.getCandidate().getName())
        .email(domain.getCandidate().getEmail()).score(domain.getScore()).build();
  }
}
