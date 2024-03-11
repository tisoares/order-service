package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.base.BaseUpdate;

public interface OrderUpdate extends BaseUpdate<Order> {

    Order execute(Long id, OrderPayload payload);
}
