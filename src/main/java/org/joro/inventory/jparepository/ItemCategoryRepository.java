package org.joro.inventory.jparepository;

import org.joro.inventory.jpamodel.dto.ItemCategoryDto;
import org.joro.inventory.jpamodel.entity.ItemCategoryEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemCategoryRepository extends LongKeyRepository<ItemCategoryEntity> {

    List<ItemCategoryDto> findAll(Sort sort);

    ItemCategoryDto findByItemCategoryKey(Long itemCategoryKey);

    ItemCategoryDto saveAndFlush(ItemCategoryEntity itemCategoryEntity);

    void delete(ItemCategoryEntity itemCategoryEntity);
}
