package com.tisoares.oderservice.internal.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;

public interface BaseCreate<T extends BaseEntity> {
    T execute(T newEntity);
}
