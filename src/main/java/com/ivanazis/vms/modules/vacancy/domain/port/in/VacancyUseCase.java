package com.ivanazis.vms.modules.vacancy.domain.port.in;

import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyUseCase {
  Vacancy createVacancy(Vacancy vacancy);

  Page<Vacancy> getAllVacancies(Pageable pageable);

  Vacancy getVacancyById(String id);

  Vacancy updateVacancy(String id, Vacancy vacancy);

  void deleteVacancy(String id);
}