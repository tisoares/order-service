package com.tisoares.oderservice.external.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPayload {

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String name;
}
