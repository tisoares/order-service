package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Order;

public interface OrderProcess {

    Order execute(Order order);

    void execute();
}
