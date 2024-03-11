package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseDeleteImpl;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.ItemDelete;
import org.springframework.stereotype.Service;

@Service
public class ItemDeleteImpl extends BaseDeleteImpl<Item> implements ItemDelete {
    public ItemDeleteImpl(BaseRepository<Item> repository) {
        super(repository);
    }
}
