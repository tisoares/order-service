package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseRetrieveImpl;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.repository.OrderRepository;
import com.tisoares.oderservice.internal.usecase.OrderRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnSingleCandidate(OrderRetrieve.class)
public class OrderRetrieveImpl extends BaseRetrieveImpl<Order> implements OrderRetrieve {

    private final OrderRepository orderRepository;

    public OrderRetrieveImpl(OrderRepository repository, EntitySpecification<Order> entitySpecification) {
        super(repository, entitySpecification);
        this.orderRepository = repository;
    }

    @Override
    public List<Order> execute() {
        return orderRepository.getOrdersNotCompleted();
    }
}
