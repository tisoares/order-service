package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.api.OrderController;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.OrderCreate;
import com.tisoares.oderservice.internal.usecase.OrderDelete;
import com.tisoares.oderservice.internal.usecase.OrderRetrieve;
import com.tisoares.oderservice.internal.usecase.OrderUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
@ConditionalOnSingleCandidate(OrderController.class)
public class OrderControllerImpl implements OrderController {

    private final OrderRetrieve orderRetrieve;
    private final OrderCreate orderCreate;
    private final OrderUpdate orderUpdate;
    private final OrderDelete orderDelete;


    @Override
    public Optional<Order> getById(Long id) {
        return orderRetrieve.execute(id);
    }

    @Override
    public Page<Order> getAll(Pageable pageable, SearchCriteria searchCriteria) {
        return orderRetrieve.execute(pageable, searchCriteria);
    }

    @Override
    public Order create(OrderPayload order) {
        return orderCreate.execute(order);
    }

    @Override
    public Order update(Long id, OrderPayload order) {
        return orderUpdate.execute(id, order);
    }

    @Override
    public void delete(Long id) {
        orderDelete.execute(id);
    }
}
