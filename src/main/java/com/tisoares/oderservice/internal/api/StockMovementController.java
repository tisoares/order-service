package com.tisoares.oderservice.internal.api;


import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.StockMovement;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "stock-movements", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Stock Movement", tags = {"Stock Movement"}, description = "Stock Movement")
public interface StockMovementController {
    @GetMapping("/{id}")
    Optional<StockMovement> getById(@PathVariable Long id);

    @GetMapping
    Page<StockMovement> getAll(Pageable pageable, SearchCriteria<StockMovement> searchCriteria);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    StockMovement create(@RequestBody StockMovementPayload stockMovement);

    @PutMapping("/{id}")
    StockMovement update(@PathVariable Long id, @RequestBody StockMovementPayload stockMovement);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
