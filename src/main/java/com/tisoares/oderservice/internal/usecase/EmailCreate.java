package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.base.BaseCreate;

public interface EmailCreate extends BaseCreate<Email> {

    Email execute(Order order);
}
