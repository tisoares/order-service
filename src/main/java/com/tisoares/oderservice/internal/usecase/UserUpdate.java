package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.base.BaseUpdate;

public interface UserUpdate extends BaseUpdate<User> {
    User execute(Long id, UserPayload user);
}
