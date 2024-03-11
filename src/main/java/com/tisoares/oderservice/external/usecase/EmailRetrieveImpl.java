package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseRetrieveImpl;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.enums.EmailStatus;
import com.tisoares.oderservice.internal.repository.EmailRepository;
import com.tisoares.oderservice.internal.usecase.EmailRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnSingleCandidate(EmailRetrieve.class)
public class EmailRetrieveImpl extends BaseRetrieveImpl<Email> implements EmailRetrieve {

    private final EmailRepository emailRepository;

    public EmailRetrieveImpl(EmailRepository repository, EntitySpecification<Email> entitySpecification) {
        super(repository, entitySpecification);
        this.emailRepository = repository;
    }

    /**
     * Return First 50 emails pending to send
     *
     * @return List of Emails pending to send
     */
    @Override
    public List<Email> execute() {
        return emailRepository.findTop50ByStatusOrderByCreatedAtAsc(EmailStatus.PENDING);
    }
}
