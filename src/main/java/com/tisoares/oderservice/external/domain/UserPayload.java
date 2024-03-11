package com.tisoares.oderservice.external.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayload {

    private Long id;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @NotEmpty
    @Size(max = 150)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String password;
}
