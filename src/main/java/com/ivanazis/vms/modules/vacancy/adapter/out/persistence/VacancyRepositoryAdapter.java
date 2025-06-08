package com.ivanazis.vms.modules.vacancy.adapter.out.persistence;

import com.ivanazis.vms.modules.vacancy.domain.model.Criteria;
import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import com.ivanazis.vms.modules.vacancy.domain.port.out.VacancyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VacancyRepositoryAdapter implements VacancyRepositoryPort {

  private final VacancyMongoRepository mongoRepository;

  @Override
  public Vacancy save(Vacancy vacancy) {
    VacancyDocument document = toDocument(vacancy);
    VacancyDocument savedDocument = mongoRepository.save(document);
    return toDomain(savedDocument);
  }

  @Override
  public Page<Vacancy> findAll(Pageable pageable) {
    return mongoRepository.findAll(pageable).map(this::toDomain);
  }

  @Override
  public Optional<Vacancy> findById(String id) {
    return mongoRepository.findById(id).map(this::toDomain);
  }

  @Override
  public void deleteById(String id) {
    mongoRepository.deleteById(id);
  }

  private VacancyDocument toDocument(Vacancy vacancy) {
    VacancyDocument doc = new VacancyDocument();
    doc.setId(vacancy.getId());
    doc.setName(vacancy.getName());
    if (vacancy.getCriteria() != null) {
      doc.setCriteria(vacancy.getCriteria().stream()
          .map(this::toCriteriaDocument)
          .collect(Collectors.toList()));
    }
    return doc;
  }

  private Vacancy toDomain(VacancyDocument doc) {
    return Vacancy.builder()
        .id(doc.getId())
        .name(doc.getName())
        .criteria(doc.getCriteria() != null ? doc.getCriteria().stream()
            .map(this::toCriteriaDomain)
            .collect(Collectors.toList()) : null)
        .build();
  }

  private CriteriaDocument toCriteriaDocument(Criteria criteria) {
    CriteriaDocument doc = new CriteriaDocument();
    doc.setName(criteria.getName());
    doc.setWeight(criteria.getWeight());
    doc.setDetails(criteria.getDetails());
    return doc;
  }

  private Criteria toCriteriaDomain(CriteriaDocument doc) {
    return Criteria.builder()
        .name(doc.getName())
        .weight(doc.getWeight())
        .details(doc.getDetails())
        .build();
  }
}
