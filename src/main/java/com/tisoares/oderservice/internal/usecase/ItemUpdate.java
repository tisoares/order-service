package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.usecase.base.BaseUpdate;

public interface ItemUpdate extends BaseUpdate<Item> {

    Item execute(Long id, ItemPayload payload);

}
