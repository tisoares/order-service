package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.StockMovement;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockMovementRepository extends BaseRepository<StockMovement> {

    @Query("select s from StockMovement s join fetch s.item i where s.available > 0 and i.id = :itemId")
    List<StockMovement> getStockMovementAvailableByItem(Long itemId);

}
