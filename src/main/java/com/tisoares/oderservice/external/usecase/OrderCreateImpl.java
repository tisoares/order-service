package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.external.domain.mapper.OrderMapper;
import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.OrderCreate;
import com.tisoares.oderservice.internal.usecase.OrderProcess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@ConditionalOnSingleCandidate(OrderCreate.class)
public class OrderCreateImpl extends BaseCreateImpl<Order> implements OrderCreate {

    private final OrderMapper orderMapper;
    private final OrderProcess orderProcess;

    public OrderCreateImpl(BaseRepository<Order> repository, OrderMapper orderMapper, OrderProcess orderProcess) {
        super(repository);
        this.orderMapper = orderMapper;
        this.orderProcess = orderProcess;
    }

    @Override
    @Transactional
    public Order execute(Order newEntity) {
        Order result = super.execute(newEntity);
        return orderProcess.execute(result);
    }

    @Override
    @Transactional
    public Order execute(@Valid OrderPayload payload) {
        return this.execute(orderMapper.OrderPayloadToOrder(payload));
    }
}
