package be.boets.addresstool.search;

import be.boets.addresstool.person.PersonEntity;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecs {

    public static Specification<PersonEntity> searchByCriteria(SearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Direct Fields
            addLikePredicate(predicates, cb, root.get("lastName"), criteria.name());
            addLikePredicate(predicates, cb, root.get("firstName"), criteria.firstName());

            // 2. Joins (Crucial for performance and avoiding Cartesian products)
            var address = root.join("addressEntity", JoinType.LEFT);
            var city = address.join("cityEntity", JoinType.LEFT);

            addLikePredicate(predicates, cb, address.get("street"), criteria.street());
            addLikePredicate(predicates, cb, address.get("number"), criteria.number() != null ? criteria.number() : null);
            addLikePredicate(predicates, cb, city.get("postalCode"), criteria.postalCode());
            addLikePredicate(predicates, cb, city.get("name"), criteria.city());

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Helper to handle null checks and case-insensitive LIKE queries
     */
    private static void addLikePredicate(List<Predicate> predicates, jakarta.persistence.criteria.CriteriaBuilder cb, Path<String> path, String value) {
        if (StringUtils.hasText(value)) {
            predicates.add(cb.like(cb.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }
}
