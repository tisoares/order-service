package com.tisoares.oderservice.external.domain.mapper;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import com.tisoares.oderservice.internal.usecase.ItemRetrieve;
import com.tisoares.oderservice.internal.usecase.UserAuthenticatedRetrieve;
import com.tisoares.oderservice.internal.usecase.UserRetrieve;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final UserAuthenticatedRetrieve userAuthenticatedRetrieve;
    private final ItemRetrieve itemRetrieve;
    private final UserRetrieve userRetrieve;

    public Order OrderPayloadToOrder(OrderPayload payload) {
        return Order.builder()
                .item(itemRetrieve.execute(
                        Item.builder()
                                .id(payload.getItem().getId())
                                .name(payload.getItem().getName())
                                .build()))
                .quantity(payload.getQuantity())
                .user(userRetrieve.execute(
                        userAuthenticatedRetrieve.execute()
                                .orElseThrow(() -> new BaseNotFoundException("User not found"))))
                .build();
    }

}
