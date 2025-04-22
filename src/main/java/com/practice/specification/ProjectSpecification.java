package com.practice.specification;

import com.practice.entity.Project;
import com.practice.req.ProjectSearchReq;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {
    public static Specification<Project> filter(ProjectSearchReq req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getKeyword() != null && !req.getKeyword().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("projectName")), "%" + req.getKeyword().toLowerCase() + "%"));
            }

            if (req.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), req.getStatus()));
            }

            if (req.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createDate"), req.getFromDate()));
            }

            if (req.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createDate"), req.getToDate()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
