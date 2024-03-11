package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.base.BaseCreate;

public interface OrderCreate extends BaseCreate<Order> {

    Order execute(OrderPayload payload);
}
