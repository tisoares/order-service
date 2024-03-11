package com.tisoares.oderservice.external.api;

import com.tisoares.oderservice.external.domain.ItemPayload;
import com.tisoares.oderservice.external.domain.SearchCriteria;
import com.tisoares.oderservice.internal.api.ItemController;
import com.tisoares.oderservice.internal.domain.Item;
import com.tisoares.oderservice.internal.usecase.ItemCreate;
import com.tisoares.oderservice.internal.usecase.ItemDelete;
import com.tisoares.oderservice.internal.usecase.ItemRetrieve;
import com.tisoares.oderservice.internal.usecase.ItemUpdate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@AllArgsConstructor
@ConditionalOnSingleCandidate(ItemController.class)
public class ItemControllerImpl implements ItemController {

    private final ItemCreate itemCreate;
    private final ItemRetrieve itemRetrieve;
    private final ItemUpdate itemUpdate;
    private final ItemDelete itemDelete;

    @Override
    public Optional<Item> getById(Long id) {
        return itemRetrieve.execute(id);
    }

    @Override
    public Page<Item> getAll(Pageable pageable, SearchCriteria searchCriteria) {
        return itemRetrieve.execute(pageable, searchCriteria);
    }

    @Override
    public Item create(@Valid ItemPayload item) {
        return itemCreate.execute(item);
    }

    @Override
    public Item update(Long id, @Valid ItemPayload item) {
        return itemUpdate.execute(id, item);
    }

    @Override
    public void delete(Long id) {
        itemDelete.execute(id);
    }
}
