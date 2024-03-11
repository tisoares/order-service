package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.external.domain.mapper.UserMapper;
import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.UserUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@ConditionalOnSingleCandidate(UserUpdate.class)
public class UserUpdateImpl extends BaseUpdateImpl<User> implements UserUpdate {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserUpdateImpl(BaseRepository<User> repository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(repository);
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(Long id, @Valid UserPayload user) {
        return super.execute(id, userMapper.userPayloadToUser(user));
    }
}
