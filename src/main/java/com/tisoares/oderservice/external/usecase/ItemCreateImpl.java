package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.mapper.ItemMapper;
import com.tisoares.oderservice.external.usecase.base.BaseCreateImpl;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.ItemCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@ConditionalOnSingleCandidate(ItemCreate.class)
public class ItemCreateImpl extends BaseCreateImpl<Item> implements ItemCreate {

    private final ItemMapper itemMapper;

    public ItemCreateImpl(BaseRepository<Item> repository, ItemMapper itemMapper) {
        super(repository);
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional
    public Item execute(@Valid ItemPayload itemPayload) {
        return this.execute(this.itemMapper.ItemPayloadToItem(itemPayload));
    }
}
