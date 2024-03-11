package com.tisoares.oderservice.internal.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;

public interface BaseUpdate<T extends BaseEntity> {

    T execute(T entity);

    T execute(Long id, T entity);
}
