package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.repository.EmailRepository;
import com.tisoares.oderservice.internal.usecase.EmailCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@ConditionalOnSingleCandidate(EmailCreate.class)
public class EmailCreateImpl extends BaseCreateImpl<Email> implements EmailCreate {

    public EmailCreateImpl(EmailRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public Email execute(Order order) {
        Email email = Email.builder()
                .toSend(order.getUser().getEmail())
                .subject(String.format(OrderServiceConstants.ORDER_SUBJECT, order.getId()))
                .body(makeBodyFromOrder(order))
                .build();
        return this.execute(email);
    }

    private String makeBodyFromOrder(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append("<p>Your order id: ").append(order.getId()).append(" is completed!</p>");

        return sb.toString();
    }
}
