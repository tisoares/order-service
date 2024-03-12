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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.List;

//@DirtiesContext
@AutoConfigureMockMvc
@DatabaseSetup(value = "classpath:/dbtest/other/email-send.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "classpath:/dbtest/other/email-send.xml", type = DatabaseOperation.TRUNCATE_TABLE)
public class EmailProcessTest extends BaseIntegratedTest {

    @SpyBean
//    private EmailSender emailSender;
    private JavaMailSender emailSender;

    @Autowired
    private EmailRetrieve emailRetrieve;

    @Autowired
    private EmailProcess emailProcess;

    @Test
    @Transactional
    void emailSend() {
        Mockito.clearInvocations(emailSender);
        Mockito.doNothing().when(emailSender).send(Mockito.any(MimeMessage.class));

        Email email = emailRetrieve.execute(5L).orElseThrow(() -> new BaseNotFoundException("Email not found!"));

        Email result = emailProcess.execute(email);

        Assertions.assertEquals(EmailStatus.SENT, result.getStatus());
        Assertions.assertEquals(0, result.getAttempt());

        Mockito.verify(emailSender, Mockito.times(1)).send(Mockito.any(MimeMessage.class));
        Mockito.verify(emailSender, Mockito.times(1)).createMimeMessage();
    }

    @Test
    @Transactional
    void emailSendFail() {
        Mockito.clearInvocations(emailSender);
        Mockito.doThrow(new MailSendException("Test exception")).when(emailSender).send(Mockito.any(MimeMessage.class));

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

        Mockito.verify(emailSender, Mockito.times(3)).send(Mockito.any(MimeMessage.class));
        Mockito.verify(emailSender, Mockito.times(3)).createMimeMessage();
    }


    @Test
    @Transactional
    void emailSendAllSuccess() {
        Mockito.clearInvocations(emailSender);
        Mockito.doNothing().when(emailSender).send(Mockito.any(MimeMessage.class));

        List<Email> before = emailRetrieve.execute();

        Assertions.assertEquals(2, before.size());

        emailProcess.execute();

        List<Email> after = emailRetrieve.execute();

        Assertions.assertEquals(0, after.size());

        Mockito.verify(emailSender, Mockito.times(2)).send(Mockito.any(MimeMessage.class));
        Mockito.verify(emailSender, Mockito.times(2)).createMimeMessage();
    }

    @Test
    @Transactional
    void emailSendAllFail() {
        Mockito.clearInvocations(emailSender);
        Mockito.doNothing().doThrow(new MailSendException("Test exception")).when(emailSender).send(Mockito.any(MimeMessage.class));

        List<Email> before = emailRetrieve.execute();

        Assertions.assertEquals(2, before.size());

        emailProcess.execute();

        List<Email> after = emailRetrieve.execute();

        Assertions.assertEquals(0, after.size());

        Mockito.verify(emailSender, Mockito.times(4)).send(Mockito.any(MimeMessage.class));
        Mockito.verify(emailSender, Mockito.times(4)).createMimeMessage();
    }
}
