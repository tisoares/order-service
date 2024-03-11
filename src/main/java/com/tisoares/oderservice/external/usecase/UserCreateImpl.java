package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.external.domain.mapper.UserMapper;
import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.UserCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@ConditionalOnSingleCandidate(UserCreate.class)
public class UserCreateImpl extends BaseCreateImpl<User> implements UserCreate {

    private final UserMapper userMapper;

    public UserCreateImpl(BaseRepository<User> repository, UserMapper userMapper) {
        super(repository);
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public User execute(@Valid UserPayload user) {
        return this.execute(userMapper.userPayloadToUser(user));
    }
}
