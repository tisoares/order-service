package com.tisoares.oderservice.internal.api;


import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.StockMovement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "stock-movements", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Stock Movement", tags = {"Stock Movement"}, description = "Stock Movement")
public interface StockMovementController {
    @GetMapping("/{id}")
    Optional<StockMovement> getById(@PathVariable Long id);

    @GetMapping
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "$page", value = "Page you want to retrieve (0..N)",
                    paramType = "query", dataType = "integer", defaultValue = "0", example = "0", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "$size", value = "Number of records per page.",
                    paramType = "query", dataType = "integer", defaultValue = "0", example = "50", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "$sort", value = "Sort by. E.g: id desc, foo asc",
                    paramType = "query", dataType = "string", dataTypeClass = String.class),
            @ApiImplicitParam(name = "$filter", value = "Search filters.\n\n" +
                    "* field = any attribute os model\n\n" +
                    "* condition: EQUALS|NOT_EQUALS|LIKE|LESS_THAN|GREATER_THAN\n\n" +
                    "* value: value for research. For dates use this format \"yyyy-MM-dd'T'HH:mm:ss\"\n\n" +
                    "Use comma(,) to separate field,condition,value.\n\n" +
                    "Use semicolon(;) so separate each conditions.\n\n" +
                    " E.g: field,condition,value;foo,equals,bar",
                    paramType = "query", dataType = "string", allowMultiple = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "$expand", value = "Expand dependencies. Fetch lazy collection or objects.\n\n" +
                    "Use comma(,) to separate attributes\n\n" +
                    "E.g: field,foo,bar",
                    paramType = "query", dataType = "string", allowMultiple = true, dataTypeClass = String.class)
    })
    Page<StockMovement> getAll(@ApiIgnore Pageable pageable, @ApiIgnore SearchCriteria<StockMovement> searchCriteria);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    StockMovement create(@RequestBody StockMovementPayload stockMovement);

    @PutMapping("/{id}")
    StockMovement update(@PathVariable Long id, @RequestBody StockMovementPayload stockMovement);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
