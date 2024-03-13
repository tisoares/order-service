package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface OrderRepository extends BaseRepository<Order> {
    @Query("select distinct o from Order o " +
            "join fetch o.item i " +
            "join fetch o.user u " +
            "left join fetch o.orderHistory h " +
            "where o.orderStatus != com.tisoares.oderservice.internal.domain.enums.OrderStatus.COMPLETED " +
            "order by o.createdAt asc, o.item.id ")
    Stream<Order> getOrdersNotCompleted();
}
