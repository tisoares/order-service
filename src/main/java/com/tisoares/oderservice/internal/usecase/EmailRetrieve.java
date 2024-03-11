package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.usecase.base.BaseRetrieve;

import java.util.List;

public interface EmailRetrieve extends BaseRetrieve<Email> {

    /**
     * Return First 50 emails pending to send
     *
     * @return List of Emails pending to send
     */
    List<Email> execute();

}
