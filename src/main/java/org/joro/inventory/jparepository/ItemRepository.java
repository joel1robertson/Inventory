package org.joro.inventory.jparepository;

import org.joro.inventory.jpamodel.dto.ItemDetailDto;
import org.joro.inventory.jpamodel.dto.ItemGalleryDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemRepository extends LongKeyRepository<ItemEntity> {

    List<ItemDetailDto> findAll(Sort sort);

    ItemDetailDto findByItemKey(Long itemKey);

    ItemDetailDto saveAndFlush(ItemEntity itemEntity);

    void delete(ItemEntity itemEntity);

    ItemGalleryDto findItemGalleryDtoByItemKey(Long itemKey);
}
