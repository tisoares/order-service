package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.api.EmailController;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.usecase.EmailDelete;
import com.tisoares.oderservice.internal.usecase.EmailRetrieve;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@ConditionalOnSingleCandidate(EmailController.class)
public class EmailControllerImpl implements EmailController {

    private final EmailRetrieve emailRetrieve;
    private final EmailDelete emailDelete;

    @Override
    public Optional<Email> getById(Long id) {
        return emailRetrieve.execute(id);
    }

    @Override
    public Page<Email> getAll(Pageable pageable, SearchCriteria searchCriteria) {
        return emailRetrieve.execute(pageable, searchCriteria);
    }

    @Override
    public void delete(Long id) {
        emailDelete.execute(id);
    }
}
