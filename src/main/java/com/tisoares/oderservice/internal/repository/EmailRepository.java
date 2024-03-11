package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.enums.EmailStatus;
import org.hibernate.LockOptions;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface EmailRepository extends BaseRepository<Email> {
    @QueryHints({@QueryHint(name = AvailableSettings.JPA_LOCK_TIMEOUT, value = "" + LockOptions.SKIP_LOCKED)})
    @Lock(LockModeType.OPTIMISTIC)
    List<Email> findTop50ByStatusOrderByCreatedAtAsc(EmailStatus emailStatus);
}
