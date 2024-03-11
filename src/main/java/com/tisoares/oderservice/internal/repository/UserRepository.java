package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email);
}
