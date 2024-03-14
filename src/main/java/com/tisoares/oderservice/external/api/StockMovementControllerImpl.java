package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.api.StockMovementController;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.StockMovementCreate;
import com.tisoares.oderservice.internal.usecase.StockMovementDelete;
import com.tisoares.oderservice.internal.usecase.StockMovementRetrieve;
import com.tisoares.oderservice.internal.usecase.StockMovementUpdate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class StockMovementControllerImpl implements StockMovementController {

    private final StockMovementCreate stockMovementCreate;
    private final StockMovementUpdate stockMovementUpdate;
    private final StockMovementRetrieve stockMovementRetrieve;
    private final StockMovementDelete stockMovementDelete;

    @Override
    public Optional<StockMovement> getById(Long id) {
        return stockMovementRetrieve.execute(id);
    }

    @Override
    public Page<StockMovement> getAll(Pageable pageable, SearchCriteria<StockMovement> searchCriteria) {
        return stockMovementRetrieve.execute(pageable, searchCriteria);
    }

    @Override
    public StockMovement create(StockMovementPayload stockMovement) {
        return stockMovementCreate.execute(stockMovement);
    }

    @Override
    public StockMovement update(Long id, StockMovementPayload stockMovement) {
        return stockMovementUpdate.execute(id, stockMovement);
    }

    @Override
    public void delete(Long id) {
        stockMovementDelete.execute(id);
    }
}
