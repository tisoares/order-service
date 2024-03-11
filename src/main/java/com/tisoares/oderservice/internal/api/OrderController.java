package com.tisoares.oderservice.internal.api;


import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Order;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Order", tags = {"Order"}, description = "Order")
public interface OrderController {
    @GetMapping("/{id}")
    Optional<Order> getById(@PathVariable Long id);

    @GetMapping
    Page<Order> getAll(Pageable pageable, SearchCriteria searchCriteria);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Order create(@RequestBody OrderPayload order);

    @PutMapping("/{id}")
    Order update(@PathVariable Long id, @RequestBody OrderPayload order);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
