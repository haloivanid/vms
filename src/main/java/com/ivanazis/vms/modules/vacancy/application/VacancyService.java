package com.ivanazis.vms.modules.vacancy.application;

import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import com.ivanazis.vms.modules.vacancy.domain.port.in.VacancyUseCase;
import com.ivanazis.vms.modules.vacancy.domain.port.out.VacancyRepositoryPort;
import com.ivanazis.vms.modules.vacancy.exception.VacancyNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VacancyService implements VacancyUseCase {

  private final VacancyRepositoryPort vacancyRepository;

  @Override
  public Vacancy createVacancy(Vacancy vacancy) {
    return vacancyRepository.save(vacancy);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Vacancy> getAllVacancies(Pageable pageable) {
    return vacancyRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Vacancy getVacancyById(String id) {
    Vacancy vacancy = vacancyRepository.findById(id)
        .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found with id: " + id));

    return vacancy;

  }

  @Override
  public Vacancy updateVacancy(String id, Vacancy vacancyUpdates) {
    Vacancy vacancy = vacancyRepository.findById(id)
        .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found with id: " + id));

    vacancy.setName(vacancyUpdates.getName());
    vacancy.setCriteria(vacancyUpdates.getCriteria());
    vacancyRepository.save(vacancy);

    return vacancy;
  }

  @Override
  public void deleteVacancy(String id) {
    vacancyRepository.deleteById(id);
  }
}
