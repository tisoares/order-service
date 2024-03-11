package com.tisoares.oderservice.external.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.base.BaseCreate;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.Valid;

@AllArgsConstructor
public abstract class BaseCreateImpl<T extends BaseEntity> implements BaseCreate<T> {

    protected final BaseRepository<T> repository;

    @Override
    @Transactional
    public T execute(@Valid T newEntity) {
        return repository.save(newEntity);
    }
}
