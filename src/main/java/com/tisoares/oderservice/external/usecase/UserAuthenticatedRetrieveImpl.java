package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.UserAuthenticatedRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@ConditionalOnSingleCandidate(UserAuthenticatedRetrieve.class)
public class UserAuthenticatedRetrieveImpl implements UserAuthenticatedRetrieve {
    @Override
    public Optional<User> execute() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (o instanceof User) {
            return Optional.of((User) SecurityContextHolder.getContext().getAuthentication().getDetails());
        }
        return Optional.empty();
    }
}
