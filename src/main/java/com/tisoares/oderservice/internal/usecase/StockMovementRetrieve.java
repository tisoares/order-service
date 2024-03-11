package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;

import java.util.List;

public interface StockMovementRetrieve extends BaseRetrieve<StockMovement> {

    List<StockMovement> execute(Item item);

}
