package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseRetrieveImpl;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.repository.StockMovementRepository;
import com.tisoares.oderservice.internal.usecase.StockMovementRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnSingleCandidate(StockMovementRetrieve.class)
public class
StockMovementRetrieveImpl extends BaseRetrieveImpl<StockMovement> implements StockMovementRetrieve {

    private final StockMovementRepository smRepository;

    public StockMovementRetrieveImpl(StockMovementRepository repository, EntitySpecification<StockMovement> entitySpecification) {
        super(repository, entitySpecification);
        this.smRepository = repository;
    }

    @Override
    public List<StockMovement> execute(Item item) {
        return smRepository.getStockMovementAvailableByItem(item.getId());
    }
}
