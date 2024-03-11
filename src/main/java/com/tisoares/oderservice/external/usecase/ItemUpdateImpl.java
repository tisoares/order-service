package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.mapper.ItemMapper;
import com.tisoares.oderservice.external.usecase.base.BaseUpdateImpl;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.ItemUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(ItemUpdate.class)
public class ItemUpdateImpl extends BaseUpdateImpl<Item> implements ItemUpdate {

    private final ItemMapper itemMapper;

    public ItemUpdateImpl(BaseRepository<Item> repository, ItemMapper itemMapper) {
        super(repository);
        this.itemMapper = itemMapper;
    }

    @Override
    public Item execute(Long id, ItemPayload payload) {
        return super.execute(id, itemMapper.ItemPayloadToItem(payload));
    }
}
