package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.usecase.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
@ConditionalOnSingleCandidate(EmailSender.class)
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;
    private final String senderEmail;

    public EmailSenderImpl(JavaMailSender mailSender,
                           @Value("spring.mail.username") String senderEmail) {
        this.mailSender = mailSender;
        this.senderEmail = senderEmail;
    }

    @Override
    public boolean execute(Email email) {
        try {
            sendEmail(email.getToSend(), email.getSubject(), email.getBody());
        } catch (Exception e) {
            logger.error(String.format("Fail to send email to %s. Error: %s", email.getToSend(), e.getMessage()), e);
            return false;
        }
        return true;
    }

    private void sendEmail(String email, String subject, String content) throws
            MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(this.senderEmail, OrderServiceConstants.EMAIL_NAME);
        helper.setTo(email);

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

}
