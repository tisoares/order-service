package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseDeleteImpl;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.UserDelete;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(UserDelete.class)
public class UserDeleteImpl extends BaseDeleteImpl<User> implements UserDelete {
    public UserDeleteImpl(BaseRepository<User> repository) {
        super(repository);
    }
}
