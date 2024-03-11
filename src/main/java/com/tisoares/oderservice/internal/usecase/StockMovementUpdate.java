package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.base.BaseUpdate;

public interface StockMovementUpdate extends BaseUpdate<StockMovement> {

    StockMovement execute(Long id, StockMovementPayload payload);
}
