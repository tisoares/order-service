package com.tisoares.oderservice.external.usecase.base;

import com.tisoares.oderservice.internal.domain.BaseEntity;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.base.BaseDelete;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseDeleteImpl<T extends BaseEntity> implements BaseDelete<T> {

    protected final BaseRepository<T> repository;

    @Override
    @Transactional
    public void execute(Long id) {
        Optional<T> entity = repository.findById(id);
        if (!entity.isPresent()) {
            throw new BaseNotFoundException("Not found to delete");
        }
        beforeDelete(entity.get());
        repository.delete(entity.get());
    }

    public void beforeDelete(T entity) {
        // Override to check if can delete
    }

}
