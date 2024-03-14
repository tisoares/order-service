package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.domain.BaseEntity;
import com.tisoares.oderservice.internal.exception.BaseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EntitySpecification<T extends BaseEntity> {

    public Specification<T> specificationBuilder(SearchCriteria<T> searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Long.class != query.getResultType() && Objects.nonNull(searchCriteria)
                    && !CollectionUtils.isEmpty(searchCriteria.getExpands())) {
                for (String e : searchCriteria.getExpands()) {
                    Field f = getField(e, searchCriteria.getClazz());
                    if (Collection.class.isAssignableFrom(f.getType())) {
                        root.fetch(e, JoinType.LEFT);
                    } else {
                        root.fetch(e);
                    }
                }
            }

            if (Objects.nonNull(searchCriteria) && !CollectionUtils.isEmpty(searchCriteria.getFilters())) {
                List<SearchCriteria.Filter> filters = searchCriteria.getFilters();
                predicates = filters.stream()
                        .map(f -> this.createSpecification(f, searchCriteria.getClazz()))
                        .map(specificationBuilder -> specificationBuilder.toPredicate(root, query, criteriaBuilder))
                        .collect(Collectors.toList());
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<T> createSpecification(SearchCriteria.Filter filter, Class<T> clazz) {
        return (root, query, criteriaBuilder) -> {
            From from = root;
            String field = filter.getField();
            String[] dep = filter.getField().split("\\.");
            Class<?> c = clazz;
            Field fClass = null;

            if (dep.length > 1) {
                for (int i = 0; i < dep.length - 1; i++) {
                    fClass = getField(dep[i].trim(), c);
                    if (OrderServiceConstants.DOMAIN_PACKAGE.equals(fClass.getType().getPackage().getName())) {
                        from = root.join(dep[i].trim());
                        c = fClass.getType();
                    }
                }
                field = dep[dep.length - 1];
            }

            fClass = getField(field, c);

            switch (filter.getOperator()) {
                case EQUALS:
                    return criteriaBuilder.equal(from.get(field), parseEqualsValue(fClass, filter.getValue()));
                case NOT_EQUALS:
                    return criteriaBuilder.notEqual(from.get(field), parseEqualsValue(fClass, filter.getValue()));
                case GREATER_THAN:
                    return criteriaBuilder.greaterThanOrEqualTo(from.get(field), this.parseValue(fClass, filter.getValue()));
                case LESS_THAN:
                    return criteriaBuilder.lessThanOrEqualTo(from.get(field), this.parseValue(fClass, filter.getValue()));
                case LIKE:
                    return criteriaBuilder.like(from.get(field), "%" + filter.getValue() + "%");
                default:
                    throw new BaseException("Invalid operator!");
            }
        };
    }

    private Object parseEqualsValue(Field field, String value) {
        if (field.getType().isEnum()) {
            return Enum.valueOf((Class<Enum>) field.getType(), value);
        }
        return parseValue(field, value);
    }

    private Comparable parseValue(Field field, String value) {
        if (field.getType().isAssignableFrom(LocalDateTime.class)) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(OrderServiceConstants.DATE_TIME_PATTERN));
        }

        return value;
    }

    private Field getField(String f, Class<?> clazz) {
        try {
            return clazz.getDeclaredField(f);
        } catch (NoSuchFieldException e) {
            try {
                return clazz.getSuperclass().getDeclaredField(f);
            } catch (NoSuchFieldException ex) {
                throw new BaseException("Invalid Parameter", e);
            }
        }
    }

}