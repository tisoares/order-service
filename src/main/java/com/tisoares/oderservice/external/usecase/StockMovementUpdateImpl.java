package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.external.domain.mapper.StockMovementMapper;
import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.StockMovementUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(StockMovementUpdate.class)
public class StockMovementUpdateImpl extends BaseUpdateImpl<StockMovement> implements StockMovementUpdate {

    private final StockMovementMapper stockMovementMapper;

    public StockMovementUpdateImpl(BaseRepository<StockMovement> repository, StockMovementMapper stockMovementMapper) {
        super(repository);
        this.stockMovementMapper = stockMovementMapper;
    }

    @Override
    public StockMovement execute(Long id, StockMovementPayload payload) {
        return super.execute(id, stockMovementMapper.stockMovementPayloadToStockMovement(payload));
    }
}
