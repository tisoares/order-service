package com.tisoares.oderservice.external.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockMovementPayload {

    private Long id;

    @NotNull
    private ItemPayload item;

    @NotNull
    @Min(1)
    private Integer quantity;

}
