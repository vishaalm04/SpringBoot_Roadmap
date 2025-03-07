package com.example.CRUD.repository;

import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> filterUsers(String searchTerm, String status) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Status Filtering
            if (status != null && !status.equalsIgnoreCase("BOTH")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), UserStatus.valueOf(status.toUpperCase())));
            }

            // Search across multiple fields
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"));
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("emailId")), "%" + searchTerm.toLowerCase() + "%"));
                searchPredicates.add(criteriaBuilder.like(root.get("phoneNumber"), searchTerm + "%")); // Phone starts with
                searchPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueCode")), "%" + searchTerm.toLowerCase() + "%"));

                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
