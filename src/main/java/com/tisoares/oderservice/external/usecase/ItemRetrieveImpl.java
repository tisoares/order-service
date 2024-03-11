package com.tisoares.oderservice.external.usecase;

import com.tisoares.oderservice.external.usecase.base.BaseRetrieveImpl;
import com.tisoares.oderservice.internal.configuration.EntitySpecification;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.repository.BaseRepository;
import com.tisoares.oderservice.internal.usecase.ItemRetrieve;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnSingleCandidate(ItemRetrieve.class)
public class ItemRetrieveImpl extends BaseRetrieveImpl<Item> implements ItemRetrieve {
    public ItemRetrieveImpl(BaseRepository<Item> repository, EntitySpecification entitySpecification) {
        super(repository, entitySpecification);
    }
}
