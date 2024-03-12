package com.tisoares.oderservice;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class,
        MockitoTestExecutionListener.class})
@DbUnitConfiguration
//@WithMockUser(username = "admin@admin.com", password = "1234567")
@ActiveProfiles("test")
@DatabaseSetup(value = "classpath:/dbtest/general/user-general.xml", type = DatabaseOperation.CLEAN_INSERT)
public abstract class BaseIntegratedTest {

    public static final String BASIC_AUTH_USERNAME = "admin@admin.com";
    public static final String BASIC_AUTH_PASSWORD = "123456";
    public static final String BASIC_AUTH_USERNAME_AND_PASSWORD = BASIC_AUTH_USERNAME + ":" + BASIC_AUTH_PASSWORD;


    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    protected void setUpBaseTest() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .defaultRequest(get("/").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString(BASIC_AUTH_USERNAME_AND_PASSWORD.getBytes())))
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

}
