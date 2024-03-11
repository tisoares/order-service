package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.external.domain.mapper.OrderMapper;
import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.OrderUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(OrderUpdate.class)
public class OrderUpdateImpl extends BaseUpdateImpl<Order> implements OrderUpdate {

    private final OrderMapper orderMapper;

    public OrderUpdateImpl(BaseRepository<Order> repository, OrderMapper orderMapper) {
        super(repository);
        this.orderMapper = orderMapper;
    }

    @Override
    public Order execute(Long id, OrderPayload payload) {
        return super.execute(id, this.orderMapper.OrderPayloadToOrder(payload));
    }
}
