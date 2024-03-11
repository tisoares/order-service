package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseDeleteImpl;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.domain.enums.OrderStatus;
import com.tisoares.oderservice.internal.exception.OrderException;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.OrderDelete;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(OrderDelete.class)
public class OrderDeleteImpl extends BaseDeleteImpl<Order> implements OrderDelete {
    public OrderDeleteImpl(BaseRepository<Order> repository) {
        super(repository);
    }

    @Override
    public void beforeDelete(Order entity) {
        if (entity.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new OrderException("Could not delete. Order has been completed");
        }
        if (!entity.getOrderHistory().isEmpty()) {
            throw new OrderException("Could not delete. Order has been started to process.");
        }
    }
}
