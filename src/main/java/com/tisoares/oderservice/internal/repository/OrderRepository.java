package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    @Query("select o from Order o " +
            "join fetch o.item i " +
            "join fetch o.orderHistory h " +
            "where o.orderStatus != com.tisoares.oderservice.internal.domain.enums.OrderStatus.COMPLETED " +
            "order by o.createdAt asc ")
    List<Order> getOrdersNotCompleted();
}
