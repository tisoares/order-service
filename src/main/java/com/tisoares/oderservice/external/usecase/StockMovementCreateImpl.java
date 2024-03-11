package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.external.domain.mapper.StockMovementMapper;
import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.StockMovementCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@ConditionalOnSingleCandidate(StockMovementCreate.class)
public class StockMovementCreateImpl extends BaseCreateImpl<StockMovement> implements StockMovementCreate {

    private final StockMovementMapper stockMovementMapper;

    public StockMovementCreateImpl(BaseRepository<StockMovement> repository, StockMovementMapper stockMovementMapper) {
        super(repository);
        this.stockMovementMapper = stockMovementMapper;
    }

    @Override
    @Transactional
    public StockMovement execute(@Valid StockMovementPayload payload) {
        return this.execute(this.stockMovementMapper.stockMovementPayloadToStockMovement(payload));
    }
}
