package com.tisoares.oderservice.internal.api;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Email;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "emails", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Email", tags = {"Email"}, description = "Email")
public interface EmailController {
    @GetMapping("/{id}")
    Optional<Email> getById(@PathVariable Long id);

    @GetMapping
//    @ApiOperation(value = "Get All", tags = {"Get All"})
    @Operation(
            summary = "Get all registers",
            description = "Get all registers",
            tags = {"Email"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "$filter", description = "filter data", example = "field,condition,value;foo,equals,bar"),
                    @Parameter(in = ParameterIn.QUERY, name = "$expand", description = "Expand dependencies", example = "foo,bar")}
    )
    Page<Email> getAll(@ApiIgnore Pageable pageable, @ApiIgnore SearchCriteria searchCriteria);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
