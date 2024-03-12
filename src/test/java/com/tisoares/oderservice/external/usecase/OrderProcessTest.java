package com.tisoares.oderservice.external.usecase;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.usecase.EmailRetrieve;
import com.tisoares.oderservice.internal.usecase.OrderProcess;
import com.tisoares.oderservice.internal.usecase.OrderRetrieve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import javax.transaction.Transactional;
import java.util.List;

//@DirtiesContext
@AutoConfigureMockMvc
@DatabaseSetup(value = "classpath:/dbtest/general/user-general.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseSetup(value = "classpath:/dbtest/other/order-process.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "classpath:/dbtest/other/order-process.xml", type = DatabaseOperation.DELETE_ALL)
public class OrderProcessTest extends BaseIntegratedTest {

    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private OrderRetrieve orderRetrieve;
    @Autowired
    private EmailRetrieve emailRetrieve;


    @Test
    @Transactional
    void orderProcessTest() {

        List<Order> before = orderRetrieve.execute();
        Assertions.assertEquals(2, before.size());

        List<Email> emailBefore = emailRetrieve.execute();
        Assertions.assertEquals(0, emailBefore.size());

        orderProcess.execute();

        List<Order> after = orderRetrieve.execute();
        Assertions.assertEquals(0, after.size());

        List<Email> emailAfter = emailRetrieve.execute();
        Assertions.assertEquals(2, emailAfter.size());

    }

}
