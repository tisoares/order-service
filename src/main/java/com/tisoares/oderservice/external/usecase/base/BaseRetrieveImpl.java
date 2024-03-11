package com.tisoares.oderservice.external.usecase.base;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.BaseEntity;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseRetrieveImpl<T extends BaseEntity> implements BaseRetrieve<T> {

    private static final String DEFAULT_IDENTIFIER = "id";

    protected final BaseRepository<T> repository;
    protected final EntitySpecification<T> entitySpecification;

    @Override
    public Optional<T> execute(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<T> execute(Long id, String expand) {
        if (StringUtils.isBlank(expand)) {
            return this.execute(id);
        }
        return repository.findOne(entitySpecification.specificationBuilder(SearchCriteria.builder()
                .expands(Arrays.asList(expand.split(OrderServiceConstants.EXPAND_PARAMS_DELIMITER)))
                .filters(Collections.singletonList(SearchCriteria.Filter.builder()
                        .field(DEFAULT_IDENTIFIER)
                        .operator(SearchCriteria.Filter.QueryOperator.EQUALS)
                        .value(String.valueOf(id))
                        .build()))
                .build()));
    }

    @Override
    public Page<T> execute(Pageable pageable, SearchCriteria searchCriteria) {
        return repository.findAll(entitySpecification.specificationBuilder(searchCriteria), pageable);
    }


    @Override
    public T execute(T entity, String extend) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];

        if (Objects.isNull(entity)) {
            throw new BaseNotFoundException(String.format("Entity %s not found", clazz.getSimpleName()));
        }
        Optional<T> saved = execute(entity.getId(), extend);
        if (!saved.isPresent()) {
            throw new BaseNotFoundException(String.format("Entity %s %d not found", clazz.getSimpleName(), entity.getId()));
        }
        return saved.get();
    }

    public T execute(T entity) {
        return this.execute(entity, "");
    }
}
