package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EntitySpecification<T extends BaseEntity> {

    public Specification<T> specificationBuilder(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(searchCriteria) && !CollectionUtils.isEmpty(searchCriteria.getExpands())) {
                searchCriteria.getExpands().forEach(root::fetch);
            }


            if (Objects.nonNull(searchCriteria) && !CollectionUtils.isEmpty(searchCriteria.getFilters())) {
                List<SearchCriteria.Filter> filters = searchCriteria.getFilters();
                predicates = filters.stream()
                        .map(this::createSpecification)
                        .map(specificationBuilder -> specificationBuilder.toPredicate(root, query, criteriaBuilder))
                        .collect(Collectors.toList());
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<T> createSpecification(SearchCriteria.Filter filter) {
        return (root, query, criteriaBuilder) -> {
            From from = root;
            String field = filter.getField();
            String[] dep = filter.getField().split("\\.");
            if (dep.length > 1) {
                for (int i = 0; i < dep.length - 1; i++) {
                    from = root.join(dep[i].trim());
                }
                field = dep[dep.length - 1];
            }

            switch (filter.getOperator()) {
                case EQUALS:
                    return criteriaBuilder.equal(from.get(field), filter.getValue());
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(from.get(field), filter.getValue());
                case GREATER_THAN:
                    return criteriaBuilder.greaterThanOrEqualTo(from.get(field), filter.getValue());
                case LESS_THAN:
                    return criteriaBuilder.lessThanOrEqualTo(from.get(field), filter.getValue());
                case LIKE:
                    return criteriaBuilder.like(from.get(field), "%" + filter.getValue() + "%");
                default:
                    throw new IllegalArgumentException();
            }
        };
    }

}