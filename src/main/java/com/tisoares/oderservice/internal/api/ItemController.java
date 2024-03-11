package com.tisoares.oderservice.internal.api;


import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Item;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "items", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Item", tags = {"Item"}, description = "Item")
public interface ItemController {
    @GetMapping("/{id}")
    Optional<Item> getById(@PathVariable Long id);

    @GetMapping
    Page<Item> getAll(Pageable pageable, SearchCriteria searchCriteria);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Item create(@RequestBody ItemPayload item);

    @PutMapping("/{id}")
    Item update(@PathVariable Long id, @RequestBody ItemPayload item);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
