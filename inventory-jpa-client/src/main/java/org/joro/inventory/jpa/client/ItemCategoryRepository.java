package org.joro.inventory.jpa.client;

import org.joro.inventory.jpa.model.projection.ItemCategoryProjection;
import org.joro.inventory.jpa.model.entity.ItemCategoryEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemCategoryRepository extends LongKeyRepository<ItemCategoryEntity> {

    List<ItemCategoryProjection> findAll(Sort sort);

    ItemCategoryProjection findByItemCategoryKey(Long itemCategoryKey);

    ItemCategoryProjection saveAndFlush(ItemCategoryEntity itemCategoryEntity);

    void delete(ItemCategoryEntity itemCategoryEntity);
}
