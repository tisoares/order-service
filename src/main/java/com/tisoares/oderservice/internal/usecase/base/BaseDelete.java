package com.tisoares.oderservice.internal.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;

public interface BaseDelete<T extends BaseEntity> {
    void execute(Long id);
}
