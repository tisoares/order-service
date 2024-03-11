package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.StockMovementPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.usecase.ItemCreate;
import com.tisoares.oderservice.internal.usecase.StockMovementCreate;
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

public class StockMovementControllerTest extends BaseIntegratedTest {
    private static final String URL = OrderServiceConstants.URL_PREFIX_V1 + "stock-movements";

    @Autowired
    private StockMovementCreate create;

    @Autowired
    private ItemCreate itemCreate;

    @Test
    @Transactional
    void getByUuid() throws Exception {
        StockMovement expect = create.execute(createPayload());
        mockMvc.perform(get(URL + "/" + expect.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void getAll() throws Exception {
        StockMovement expect = create.execute(createPayload());
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void create() throws Exception {
        StockMovementPayload payload = createPayload();
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(payload.getQuantity())));
    }

    @Test
    @Transactional
    void update() throws Exception {
        StockMovement entity = create.execute(createPayload());
        StockMovementPayload update = StockMovementPayload.builder()
                .quantity(5)
                .item(createItem("Iphone"))
                .build();

        mockMvc.perform(put(URL + "/" + entity.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(update)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId()), Long.class))
                .andExpect(jsonPath("$.quantity", is(update.getQuantity())))
                .andExpect(jsonPath("$.item.name", is(update.getItem().getName())));
    }

    @Test
    @Transactional
    void deleteTest() throws Exception {
        StockMovement expect = create.execute(createPayload());
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(status().isOk());
    }

    private StockMovementPayload createPayload() {
        return StockMovementPayload.builder()
                .quantity(10)
                .item(createItem("Computer"))
                .build();
    }

    private ItemPayload createItem(String name) {
        Item item = itemCreate.execute(Item.builder()
                .name(name)
                .build());
        return ItemPayload.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }
}
