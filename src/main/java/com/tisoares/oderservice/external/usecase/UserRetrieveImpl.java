package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseRetrieveImpl;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.repository.UserRepository;
import com.tisoares.oderservice.internal.usecase.UserRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnSingleCandidate(UserRetrieve.class)
public class UserRetrieveImpl extends BaseRetrieveImpl<User> implements UserRetrieve {

    private final UserRepository userRepository;

    public UserRetrieveImpl(UserRepository repository, EntitySpecification<User> entitySpecification) {
        super(repository, entitySpecification);
        this.userRepository = repository;
    }

    @Override
    public Optional<User> execute(String email) {
        return userRepository.findByEmail(email);
    }
}
