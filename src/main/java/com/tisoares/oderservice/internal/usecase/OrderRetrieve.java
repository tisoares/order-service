package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;

import java.util.List;

public interface OrderRetrieve extends BaseRetrieve<Order> {
    List<Order> execute();
}
