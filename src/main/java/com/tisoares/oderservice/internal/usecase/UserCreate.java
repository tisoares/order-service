package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.domain.User;

public interface UserCreate {
    User execute(User user);

    User execute(UserPayload user);
}
