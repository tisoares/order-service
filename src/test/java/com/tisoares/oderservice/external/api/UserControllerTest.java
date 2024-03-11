package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.UserCreate;
import com.tisoares.oderservice.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends BaseIntegratedTest {
    private static final String URL = OrderServiceConstants.URL_PREFIX_V1 + "users";

    @Autowired
    private UserCreate userCreate;

    @Test
    @Transactional
    void getByUuid() throws Exception {
        User user = userCreate.execute(createUser());
        mockMvc.perform(get(URL + "/" + user.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId()), Long.class));
    }

    @Test
    @Transactional
    void getAll() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(10)));
    }

    @Test
    @Transactional
    void create() throws Exception {
        UserPayload payload = createUser();
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(payload.getName())))
                .andExpect(jsonPath("$.email", is(payload.getEmail())));
    }

    @Test
    @Transactional
    void update() throws Exception {
        User user = userCreate.execute(createUser());
        UserPayload update = UserPayload.builder()
                .name("Test")
                .email("test@test.com")
                .password("test")
                .build();

        mockMvc.perform(put(URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(update)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(update.getName())))
                .andExpect(jsonPath("$.email", is(update.getEmail())));
    }

    @Test
    @Transactional
    void updateNotFound() throws Exception {
        UserPayload payload = createUser();
        mockMvc.perform(put(URL + "/" + 5)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Id not found")));
    }

    @Test
    @Transactional
    void deleteTest() throws Exception {
        User expect = userCreate.execute(createUser());
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(status().isOk());
    }

    private UserPayload createUser() {
        return UserPayload.builder()
                .name("Tiago Soares")
                .email("tisoares@outlook.com")
                .password("12345")
                .build();
    }

}
