package be.boets.addresstool.search;

import be.boets.addresstool.address.Address;
import be.boets.addresstool.address.City;
import be.boets.addresstool.person.Person;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchSpecs {

    public static Specification<Person> searchByCriteria(SearchCriteria criteria) {
        return (root, query, cb) -> {
            if (criteria == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            containsIgnoreCase(predicates, cb, root.get("lastName"), criteria.name());
            containsIgnoreCase(predicates, cb, root.get("firstName"), criteria.firstName());

            // Use explicit joins for nested properties
            Join<Person, Address> address = root.join("address", JoinType.LEFT);
            containsIgnoreCase(predicates, cb, address.get("street"), criteria.street());

            if (criteria.number() != null) {
                predicates.add(cb.equal(address.get("number"), criteria.number()));
            }

            Join<Address, City> city = address.join("city", JoinType.LEFT);
            containsIgnoreCase(predicates, cb, city.get("postalCode"), criteria.postalCode());
            containsIgnoreCase(predicates, cb, city.get("name"), criteria.city());

            return predicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static void containsIgnoreCase(
            List<Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            Path<String> path,
            String rawValue
    ) {
        if (rawValue == null) {
            return;
        }
        String value = rawValue.trim();
        if (value.isEmpty()) {
            return;
        }

        predicates.add(
                cb.like(
                        cb.lower(path),
                        "%" + value.toLowerCase(Locale.ROOT) + "%"
                )
        );
    }
}
