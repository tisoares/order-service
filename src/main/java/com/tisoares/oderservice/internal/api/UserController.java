package com.tisoares.oderservice.internal.api;


import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.User;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = OrderServiceConstants.URL_PREFIX_V1 + "users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "User", tags = {"User"}, description = "User")
public interface UserController {
    @GetMapping("/{id}")
    Optional<User> getById(@PathVariable Long id);

    @GetMapping
    Page<User> getAll(Pageable pageable, SearchCriteria searchCriteria);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody UserPayload user);

    @PutMapping("/{id}")
    User update(@PathVariable Long id, @RequestBody UserPayload user);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
