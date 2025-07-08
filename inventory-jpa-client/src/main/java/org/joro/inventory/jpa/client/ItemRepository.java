package org.joro.inventory.jpa.client;

import org.joro.inventory.jpa.model.projection.ItemDetailProjection;
import org.joro.inventory.jpa.model.projection.ItemGalleryProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemRepository extends LongKeyRepository<ItemEntity> {

    List<ItemDetailProjection> findAll(Sort sort);

    ItemDetailProjection findByItemKey(Long itemKey);

    ItemDetailProjection saveAndFlush(ItemEntity itemEntity);

    void delete(ItemEntity itemEntity);

    ItemGalleryProjection findItemGalleryProjectionByItemKey(Long itemKey);
}
