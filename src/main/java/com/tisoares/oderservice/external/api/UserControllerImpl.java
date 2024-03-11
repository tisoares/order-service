package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.api.UserController;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.UserCreate;
import com.tisoares.oderservice.internal.usecase.UserDelete;
import com.tisoares.oderservice.internal.usecase.UserRetrieve;
import com.tisoares.oderservice.internal.usecase.UserUpdate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@ConditionalOnSingleCandidate(UserController.class)
public class UserControllerImpl implements UserController {

    private final UserRetrieve userRetrieve;
    private final UserCreate userCreate;
    private final UserUpdate userUpdate;
    private final UserDelete userDelete;

    @Override
    public Optional<User> getById(Long id) {
        return userRetrieve.execute(id);
    }

    @Override
    public Page<User> getAll(Pageable pageable, SearchCriteria searchCriteria) {
        return userRetrieve.execute(pageable, searchCriteria);
    }

    @Override
    public User create(@Valid UserPayload user) {
        return userCreate.execute(user);
    }

    @Override
    public User update(Long id, @Valid UserPayload user) {
        return userUpdate.execute(id, user);
    }

    @Override
    public void delete(Long id) {
        userDelete.execute(id);
    }
}
