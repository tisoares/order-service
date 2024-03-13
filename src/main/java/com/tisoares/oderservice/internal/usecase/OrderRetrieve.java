package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;

import java.util.stream.Stream;

public interface OrderRetrieve extends BaseRetrieve<Order> {
    Stream<Order> execute();
}
