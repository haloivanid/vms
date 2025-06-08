package com.ivanazis.vms.modules.vacancy.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "vacancies")
public class VacancyDocument {

  @Id
  private String id;
  private String name;
  private List<CriteriaDocument> criteria;
}