package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;

import java.util.Optional;

public interface UserRetrieve extends BaseRetrieve<User> {

    Optional<User> execute(String email);
}
