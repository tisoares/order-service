package com.tisoares.oderservice.internal.usecase.base;


import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.domain.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseRetrieve<T extends BaseEntity> {

    T execute(T entity);

    T execute(T entity, String expand);

    Optional<T> execute(Long id);

    Optional<T> execute(Long id, String expand);

    Page<T> execute(Pageable pageable, SearchCriteria searchCriteria);

}
