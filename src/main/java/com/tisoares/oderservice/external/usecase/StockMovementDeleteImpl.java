package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseDeleteImpl;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.exception.StockMovementException;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.StockMovementDelete;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(StockMovementDelete.class)
public class StockMovementDeleteImpl extends BaseDeleteImpl<StockMovement> implements StockMovementDelete {
    public StockMovementDeleteImpl(BaseRepository<StockMovement> repository) {
        super(repository);
    }

    @Override
    public void beforeDelete(StockMovement entity) {
        if (!entity.getAvailable().equals(entity.getQuantity())) {
            throw new StockMovementException("Could not delete this Stock Movement. It has been used.");
        }
    }
}
