package com.tisoares.oderservice.external.domain.mapper;

import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.ItemRetrieve;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StockMovementMapper {

    private final ItemRetrieve itemRetrieve;

    public StockMovement stockMovementPayloadToStockMovement(StockMovementPayload payload) {
        return StockMovement.builder()
                .item(itemRetrieve.execute(Item.builder()
                        .id(payload.getItem().getId())
                        .name(payload.getItem().getName())
                        .build()))
                .quantity(payload.getQuantity())
                .build();
    }
}
