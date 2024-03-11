package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Email;

public interface EmailProcess {
    void execute();

    Email execute(Email email);
}
