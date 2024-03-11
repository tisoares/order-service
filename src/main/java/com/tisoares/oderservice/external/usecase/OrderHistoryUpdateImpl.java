package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.OrderHistory;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.OrderHistoryUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(OrderHistoryUpdate.class)
public class OrderHistoryUpdateImpl extends BaseUpdateImpl<OrderHistory> implements OrderHistoryUpdate {
    public OrderHistoryUpdateImpl(BaseRepository<OrderHistory> repository) {
        super(repository);
    }
}
