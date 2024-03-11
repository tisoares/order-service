package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.usecase.ItemCreate;
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

public class ItemControllerTest extends BaseIntegratedTest {
    private static final String URL = OrderServiceConstants.URL_PREFIX_V1 + "items";

    @Autowired
    private ItemCreate create;

    @Test
    @Transactional
    void getByUuid() throws Exception {
        Item expect = create.execute(createPayload());
        mockMvc.perform(get(URL + "/" + expect.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void getAll() throws Exception {
        Item expect = create.execute(createPayload());
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void create() throws Exception {
        ItemPayload payload = createPayload();
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(payload.getName())));
    }

    @Test
    @Transactional
    void update() throws Exception {
        Item entity = create.execute(createPayload());
        ItemPayload update = ItemPayload.builder()
                .name("Test")
                .build();

        mockMvc.perform(put(URL + "/" + entity.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(update)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(update.getName())));
    }

    @Test
    @Transactional
    void deleteTest() throws Exception {
        Item expect = create.execute(createPayload());
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(status().isOk());
    }

    private ItemPayload createPayload() {
        return ItemPayload.builder()
                .name("Paper")
                .build();
    }

}
