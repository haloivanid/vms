package com.ivanazis.vms.modules.candidate.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository untuk CandidateDocument.
 * Menyediakan operasi CRUD siap pakai.
 */
@Repository
public interface CandidateMongoRepository extends MongoRepository<CandidateDocument, String> {
  Optional<CandidateDocument> findByEmail(String email);
}
