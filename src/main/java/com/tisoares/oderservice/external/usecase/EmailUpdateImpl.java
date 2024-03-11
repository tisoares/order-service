package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.EmailUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(EmailUpdate.class)
public class EmailUpdateImpl extends BaseUpdateImpl<Email> implements EmailUpdate {
    public EmailUpdateImpl(BaseRepository<Email> repository) {
        super(repository);
    }
}
