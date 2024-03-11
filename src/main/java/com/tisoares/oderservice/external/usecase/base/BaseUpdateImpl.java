package com.tisoares.oderservice.external.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.base.BaseUpdate;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseUpdateImpl<T extends BaseEntity> implements BaseUpdate<T> {

    protected final BaseRepository<T> repository;

    @Override
    @Transactional
    public T execute(@Valid T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T execute(Long id, @Valid T request) {
        T saved = retrieveOriginalData(id);
        beforeUpdate(request, saved);
        T toSave = copyProperties(request, saved);
        return this.execute(toSave);
    }

    protected T copyProperties(T request, T saved) {
        // Copy properties from request to saved object ro preserve basics data.
        return (T) saved.copyPropertiesUpdate(request);
    }

    protected void beforeUpdate(T request, T saved) {
        // Implement validations before update
    }

    private T retrieveOriginalData(Long id) {
        Optional<T> old = repository.findById(id);
        if (!old.isPresent()) {
            throw new BaseNotFoundException("Id not found");
        }
        return old.get();
    }

}
