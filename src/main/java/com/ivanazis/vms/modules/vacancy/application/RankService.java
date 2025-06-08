package com.ivanazis.vms.modules.vacancy.application;

import com.ivanazis.vms.modules.candidate.domain.model.Candidate;
import com.ivanazis.vms.modules.candidate.domain.port.out.CandidateRepositoryPort;
import com.ivanazis.vms.modules.vacancy.domain.model.RankedCandidate;
import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import com.ivanazis.vms.modules.vacancy.domain.port.in.RankUseCase;
import com.ivanazis.vms.modules.vacancy.domain.port.out.VacancyRepositoryPort;
import com.ivanazis.vms.modules.vacancy.exception.VacancyNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService implements RankUseCase {

  private final CandidateRepositoryPort candidateRepository;
  private final VacancyRepositoryPort vacancyRepository;

  @Override
  public Page<RankedCandidate> getRankedCandidatesForVacancy(String vacancyId, Pageable pageable) {
    Vacancy vacancy = vacancyRepository.findById(vacancyId)
        .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found with id: " + vacancyId));

    List<Candidate> allCandidates = candidateRepository.findAll(Pageable.unpaged()).getContent();

    List<RankedCandidate> rankedList = allCandidates.parallelStream()
        .map(candidate -> new RankedCandidate(candidate, vacancy.getCandidateTotalScore(candidate)))
        .sorted(Comparator.comparingInt(RankedCandidate::getScore).reversed())
        .collect(Collectors.toList());

    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), rankedList.size());

    List<RankedCandidate> pageContent = rankedList.subList(start, end);

    return new PageImpl<>(pageContent, pageable, rankedList.size());
  }
}
