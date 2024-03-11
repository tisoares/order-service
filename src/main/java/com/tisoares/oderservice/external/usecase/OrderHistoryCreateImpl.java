package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.domain.OrderHistory;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.OrderHistoryCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(OrderHistoryCreate.class)
public class OrderHistoryCreateImpl extends BaseCreateImpl<OrderHistory> implements OrderHistoryCreate {
    public OrderHistoryCreateImpl(BaseRepository<OrderHistory> repository) {
        super(repository);
    }
}
