package be.boets.addresstool.search;

import be.boets.addresstool.person.Person;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SearchSpecs {
    public static Specification<Person> searchByCriteria(SearchCriteria criteria) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.name() != null && !criteria.name().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.name().toLowerCase() + "%"));
            }

            if (criteria.firstName() != null && !criteria.firstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + criteria.firstName().toLowerCase() + "%"));
            }

//            if (criteria.birthDate() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("birthDate"), criteria.birthDate()));
//            }

            if (criteria.street() != null && !criteria.street().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("street")), "%" + criteria.street().toLowerCase() + "%"));
            }

            if (criteria.number() != null) {
                predicates.add(criteriaBuilder.like(root.get("address").get("number"), "%" + criteria.number() + "%"));
            }

            if (criteria.postalCode() != null && !criteria.postalCode().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("address").get("city").get("postalCode")),
                                "%" + criteria.postalCode().toLowerCase() + "%"
                        )
                );
            }

            if (criteria.city() != null && !criteria.city().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("address").get("city").get("name")),
                                "%" + criteria.city().toLowerCase() + "%"
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
