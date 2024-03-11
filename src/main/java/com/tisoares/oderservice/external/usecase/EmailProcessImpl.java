package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.usecase.EmailProcess;
import com.tisoares.oderservice.internal.usecase.EmailRetrieve;
import com.tisoares.oderservice.internal.usecase.EmailSender;
import com.tisoares.oderservice.internal.usecase.EmailUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@ConditionalOnSingleCandidate(EmailProcess.class)
public class EmailProcessImpl implements EmailProcess {

    private final TransactionTemplate transactionManager;
    private final EmailUpdate emailUpdate;
    private final EmailSender emailSender;
    private final EmailRetrieve emailRetrieve;

    @Override
    public void execute() {
        boolean pending = true;
        while (pending) {
            pending = Boolean.TRUE.equals(transactionManager.execute(trx -> {
                List<Email> orders = emailRetrieve.execute();
                if (orders.isEmpty()) {
                    return false;
                }
                orders.forEach(this::execute);
                return true;
            }));
        }
    }

    @Override
    @Transactional
    public Email execute(Email email) {
        if (emailSender.execute(email)) {
            email.sent();
        } else {
            email.error();
        }
        return emailUpdate.execute(email);
    }

}
