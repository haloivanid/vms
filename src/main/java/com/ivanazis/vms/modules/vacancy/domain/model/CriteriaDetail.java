package com.ivanazis.vms.modules.vacancy.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ivanazis.vms.modules.candidate.domain.model.Candidate;

/**
 * Abstract base class for all criteria details.
 * The Jackson annotations here are crucial for telling Spring/JSON
 * how to correctly deserialize JSON into the right Java object.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AgeCriteriaDetail.class, name = "age"),
        @JsonSubTypes.Type(value = GenderCriteriaDetail.class, name = "gender"),
        @JsonSubTypes.Type(value = SalaryCriteriaDetail.class, name = "salary")
})
public abstract class CriteriaDetail {
    public abstract int calculateScore(Candidate candidate, Criteria criteria);
}