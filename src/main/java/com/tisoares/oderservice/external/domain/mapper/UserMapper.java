package com.tisoares.oderservice.external.domain.mapper;

import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User userPayloadToUser(UserPayload user) {
        return User.builder(passwordEncoder)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
