package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseDeleteImpl;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.EmailDelete;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(EmailDelete.class)
public class EmailDeleteImpl extends BaseDeleteImpl<Email> implements EmailDelete {
    public EmailDeleteImpl(BaseRepository<Email> repository) {
        super(repository);
    }
}
