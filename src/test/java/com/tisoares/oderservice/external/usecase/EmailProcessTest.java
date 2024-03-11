package com.tisoares.oderservice.external.usecase;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.enums.EmailStatus;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import com.tisoares.oderservice.internal.usecase.EmailProcess;
import com.tisoares.oderservice.internal.usecase.EmailRetrieve;
import com.tisoares.oderservice.internal.usecase.EmailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.util.List;

//@DirtiesContext
@AutoConfigureMockMvc
@DatabaseSetup(value = "classpath:/dbtest/other/email-send.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "classpath:/dbtest/other/email-send.xml", type = DatabaseOperation.TRUNCATE_TABLE)
public class EmailProcessTest extends BaseIntegratedTest {

    @MockBean
    private EmailSender emailSender;

    @Autowired
    private EmailRetrieve emailRetrieve;

    @Autowired
    private EmailProcess emailProcess;

    @Test
    @Transactional
    void emailSend() {
        Mockito.clearInvocations(emailSender);
        Mockito.when(emailSender.execute(Mockito.any(Email.class))).thenReturn(true);

        Email email = emailRetrieve.execute(5L).orElseThrow(() -> new BaseNotFoundException("Email not found!"));

        Email result = emailProcess.execute(email);

        Assertions.assertEquals(EmailStatus.SENT, result.getStatus());
        Assertions.assertEquals(0, result.getAttempt());

        Mockito.verify(emailSender, Mockito.times(1)).execute(Mockito.any(Email.class));
        Mockito.verifyNoMoreInteractions(emailSender);
    }

    @Test
    @Transactional
    void emailSendFail() {
        Mockito.clearInvocations(emailSender);
        Mockito.when(emailSender.execute(Mockito.any(Email.class))).thenReturn(false);

        Email email = emailRetrieve.execute(5L).orElseThrow(() -> new BaseNotFoundException("Email not found!"));

        Email result = emailProcess.execute(email);

        Assertions.assertEquals(EmailStatus.PENDING, result.getStatus());
        Assertions.assertEquals(1, result.getAttempt());

        result = emailProcess.execute(email);
        Assertions.assertEquals(EmailStatus.PENDING, result.getStatus());
        Assertions.assertEquals(2, result.getAttempt());

        result = emailProcess.execute(email);
        Assertions.assertEquals(EmailStatus.ERROR, result.getStatus());
        Assertions.assertEquals(3, result.getAttempt());

        Mockito.verify(emailSender, Mockito.times(3)).execute(Mockito.any(Email.class));
        Mockito.verifyNoMoreInteractions(emailSender);
    }


    @Test
    @Transactional
    void emailSendAllSuccess() {
        Mockito.clearInvocations(emailSender);
        Mockito.when(emailSender.execute(Mockito.any(Email.class))).thenReturn(true);

        List<Email> before = emailRetrieve.execute();

        Assertions.assertEquals(2, before.size());

        emailProcess.execute();

        List<Email> after = emailRetrieve.execute();

        Assertions.assertEquals(0, after.size());

        Mockito.verify(emailSender, Mockito.times(2)).execute(Mockito.any(Email.class));
        Mockito.verifyNoMoreInteractions(emailSender);
    }

    @Test
    @Transactional
    void emailSendAllFail() {
        Mockito.clearInvocations(emailSender);
        Mockito.when(emailSender.execute(Mockito.any(Email.class))).thenReturn(false);

        List<Email> before = emailRetrieve.execute();

        Assertions.assertEquals(2, before.size());

        emailProcess.execute();

        List<Email> after = emailRetrieve.execute();

        Assertions.assertEquals(0, after.size());

        Mockito.verify(emailSender, Mockito.times(6)).execute(Mockito.any(Email.class));
        Mockito.verifyNoMoreInteractions(emailSender);
    }
}
