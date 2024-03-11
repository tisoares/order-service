package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Email;

public interface EmailSender {

    boolean execute(Email email);

}
