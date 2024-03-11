package com.tisoares.oderservice.internal.domain;

import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.enums.EmailStatus;
import com.tisoares.oderservice.internal.exception.BaseException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "email")
@Getter
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "email_seq", allocationSize = 50, initialValue = 100)
public class Email extends BaseEntity {

    @NotEmpty
    @Size(max = 150)
    @javax.validation.constraints.Email
    @Column(name = "to_send", nullable = false, length = 150)
    private String toSend;

    @NotEmpty
    @Size(max = 150)
    @Column(name = "subject", nullable = false, length = 150)
    private String subject;
    @NotEmpty
    @Size(max = 2500)
    @Column(name = "body", nullable = false, length = 2500)
    private String body;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatus status = EmailStatus.PENDING;

    @NotNull
    @Column(name = "attempt", nullable = false)
    private Integer attempt = 0;

    public Email() {
    }

    private Email(Builder builder) {
        setId(builder.id);
        toSend = builder.toSend;
        subject = builder.subject;
        body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Email error() {
        if (this.status != EmailStatus.PENDING) {
            throw new BaseException("Email is not correct status to error");
        }
        attempt++;
        if (OrderServiceConstants.MAX_EMAIL_ATTEMPTS.compareTo(attempt) <= 0) {
            this.status = EmailStatus.ERROR;
        }
        return this;
    }

    public Email sent() {
        if (this.status != EmailStatus.PENDING) {
            throw new BaseException("Email is not correct status to sent");
        }
        this.status = EmailStatus.SENT;
        return this;
    }

    public static final class Builder {
        private Long id;
        private @NotEmpty @Size(max = 150) @javax.validation.constraints.Email String toSend;
        private @NotEmpty @Size(max = 150) @javax.validation.constraints.Email String subject;
        private @NotEmpty @Size(max = 2500) @javax.validation.constraints.Email String body;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder toSend(@NotEmpty @Size(max = 150) @javax.validation.constraints.Email String val) {
            toSend = val;
            return this;
        }

        public Builder subject(@NotEmpty @Size(max = 150) @javax.validation.constraints.Email String val) {
            subject = val;
            return this;
        }

        public Builder body(@NotEmpty @Size(max = 2500) @javax.validation.constraints.Email String val) {
            body = val;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }
}
