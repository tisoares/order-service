package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.base.BaseCreate;

public interface StockMovementCreate extends BaseCreate<StockMovement> {

    StockMovement execute(StockMovementPayload payload);
}
