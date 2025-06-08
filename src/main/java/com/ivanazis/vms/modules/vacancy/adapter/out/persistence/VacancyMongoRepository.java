package com.ivanazis.vms.modules.vacancy.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository untuk VacancyDocument.
 * Menyediakan operasi CRUD siap pakai.
 */
@Repository
public interface VacancyMongoRepository extends MongoRepository<VacancyDocument, String> {
}
