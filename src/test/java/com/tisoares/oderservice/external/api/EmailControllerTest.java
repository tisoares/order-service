package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Email;
import com.tisoares.oderservice.internal.usecase.EmailCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmailControllerTest extends BaseIntegratedTest {
    private static final String URL = OrderServiceConstants.URL_PREFIX_V1 + "emails";

    @Autowired
    private EmailCreate create;

    @Test
    @Transactional
    void getByUuid() throws Exception {
        Email expect = create.execute(createPayload());
        mockMvc.perform(get(URL + "/" + expect.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void getAll() throws Exception {
        Email expect = createPayload();
        expect = create.execute(expect);
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void deleteTest() throws Exception {
        Email expect = create.execute(createPayload());
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(status().isOk());
    }

    private Email createPayload() {
        return Email.builder()
                .toSend("test@test.com")
                .subject("Email test")
                .body("Test Email")
                .build();
    }

}
