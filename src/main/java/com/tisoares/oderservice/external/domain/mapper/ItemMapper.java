package com.tisoares.oderservice.external.domain.mapper;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.internal.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item ItemPayloadToItem(ItemPayload itemPayload) {
        return Item.builder()
                .name(itemPayload.getName())
                .build();
    }

}
