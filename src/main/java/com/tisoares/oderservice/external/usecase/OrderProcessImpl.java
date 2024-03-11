package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.domain.OrderHistory;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.domain.enums.OrderStatus;
import com.tisoares.oderservice.internal.usecase.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@ConditionalOnSingleCandidate(OrderProcess.class)
public class OrderProcessImpl implements OrderProcess {

    private final StockMovementRetrieve stockMovementRetrieve;
    private final OrderRetrieve orderRetrieve;
    private final OrderHistoryCreate orderHistoryCreate;
    private final StockMovementUpdate stockMovementUpdate;
    private final OrderUpdate orderUpdate;
    private final EmailCreate emailCreate;

    @Override
    @Transactional
    public Order execute(Order order) {
        List<StockMovement> stockItems = stockMovementRetrieve.execute(order.getItem());

        for (StockMovement sm : stockItems) {
            Optional<OrderHistory> oh = order.addItemFromStock(sm);
            if (oh.isPresent()) {
                logger.info("Order {} used to Stock Movement {} - {} items", order.getId(), sm.getId(), oh.get().getQuantity());
                stockMovementUpdate.execute(sm);
                orderHistoryCreate.execute(oh.get());

                if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                    logger.info("Order {} is completed", order.getId());
                    emailCreate.execute(order);
                    break;
                }
            }
        }

        if (order.getOrderStatus() != OrderStatus.COMPLETED) {
            logger.info("Order {} is {}", order.getId(), order.getOrderStatus().name());
        }
        return orderUpdate.execute(order);
    }

    @Override
    @Transactional
    public void execute() {
        logger.info("Starting routine to complete orders!");
        List<Order> orders = orderRetrieve.execute();
        logger.info("Processing {} orders", orders.size());
        orders.forEach(this::execute);
        logger.info("Finished routine to complete orders!");
    }
}
