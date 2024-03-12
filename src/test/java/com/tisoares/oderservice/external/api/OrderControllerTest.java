package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.BaseIntegratedTest;
import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.OrderPayload;
import com.tisoares.oderservice.external.domain.UserPayload;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.domain.Order;
import com.tisoares.oderservice.internal.domain.StockMovement;
import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.domain.enums.OrderStatus;
import com.tisoares.oderservice.internal.usecase.ItemCreate;
import com.tisoares.oderservice.internal.usecase.OrderCreate;
import com.tisoares.oderservice.internal.usecase.StockMovementCreate;
import com.tisoares.oderservice.internal.usecase.UserCreate;
import com.tisoares.oderservice.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest extends BaseIntegratedTest {
    private static final String URL = OrderServiceConstants.URL_PREFIX_V1 + "orders";

    @Autowired
    private OrderCreate create;

    @Autowired
    private ItemCreate itemCreate;

    @Autowired
    private UserCreate userCreate;

    @Autowired
    private StockMovementCreate stockMovementCreate;

    @Test
    @Transactional
    void getByUuid() throws Exception {
        Order expect = create.execute(createPayload());
        mockMvc.perform(get(URL + "/" + expect.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void getAll() throws Exception {
        Order expect = create.execute(createPayload());
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(expect.getId()), Long.class));
    }

    @Test
    @Transactional
    void create() throws Exception {
        OrderPayload payload = createPayload();
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(payload.getQuantity())))
                .andExpect(jsonPath("$.orderStatus", is(OrderStatus.PENDING.name())));
    }

    @Test
    @Transactional
    void createAndUseStock() throws Exception {
        Item item = createItem("Mac Book");
        StockMovement stock = createStock(item, 10);
        OrderPayload payload = createPayload(item, 10);
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(payload.getQuantity())))
                .andExpect(jsonPath("$.orderStatus", is(OrderStatus.COMPLETED.name())));
    }

    @Test
    @Transactional
    void createAndUseMultipleStock() throws Exception {
        Item item = createItem("Mac Book");
        StockMovement stock1 = createStock(item, 5);
        StockMovement stock2 = createStock(item, 4);
        StockMovement stock3 = createStock(item, 4);
        OrderPayload payload = createPayload(item, 10);
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(payload)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(payload.getQuantity())))
                .andExpect(jsonPath("$.orderHistory", hasSize(3)))
                .andExpect(jsonPath("$.orderStatus", is(OrderStatus.COMPLETED.name())));
    }

    @Test
    @Transactional
    void update() throws Exception {
        Order entity = create.execute(createPayload());
        OrderPayload update = OrderPayload.builder()
                .quantity(5)
                .item(itemToItemPayload(createItem("Iphone")))
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
        Order expect = create.execute(createPayload());
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void deleteCompletedOrder() throws Exception {
        Item item = createItem("Mac Book");
        StockMovement stock = createStock(item, 10);
        OrderPayload payload = createPayload(item, 10);
        Order expect = create.execute(payload);
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Could not delete. Order has been completed")));
    }

    @Test
    @Transactional
    void deleteProcessingOrder() throws Exception {
        Item item = createItem("Mac Book");
        StockMovement stock = createStock(item, 5);
        OrderPayload payload = createPayload(item, 10);
        Order expect = create.execute(payload);
        mockMvc.perform(delete(URL + "/" + expect.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Could not delete. Order has been started to process.")));
    }

    private OrderPayload createPayload() {
        return this.createPayload(this.createItem("Computer"), 10);
    }

    private OrderPayload createPayload(Item item, Integer quantity) {
        createUser();
        return OrderPayload.builder()
                .quantity(quantity)
                .item(itemToItemPayload(item))
                .build();
    }

    private Item createItem(String name) {
        return itemCreate.execute(Item.builder()
                .name(name)
                .build());
    }

    private ItemPayload itemToItemPayload(Item item) {
        return ItemPayload.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }

    private User createUser() {
        UserPayload user = UserPayload.builder()
                .id(1L)
                .name("Tiago Soares")
                .email("tisoares@otlook.com")
                .password("1234")
                .build();

        User result = userCreate.execute(user);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("USER")));
        authToken.setDetails(result);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return result;
    }

    private StockMovement createStock(Item item, Integer quantity) {
        return stockMovementCreate.execute(StockMovement.builder()
                .item(item)
                .quantity(quantity)
                .build());
    }

}
