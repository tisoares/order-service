package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.User;

import java.util.Optional;

public interface UserAuthenticatedRetrieve {
    Optional<User> execute();
}
