package com.tisoares.oderservice.internal.usecase;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.usecase.base.BaseCreate;

public interface ItemCreate extends BaseCreate<Item> {

    Item execute(ItemPayload itemPayload);

}
