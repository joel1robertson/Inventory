package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemCategoryDto;
import org.joro.inventory.jpamodel.entity.ItemCategoryEntity;
import org.joro.inventory.model.ItemCategory;

import java.util.Optional;

public class ItemCategoryMap {

    private ItemCategoryMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemCategoryDto
    public static ItemCategory map(ItemCategoryDto itemCategoryDto) {
        return Optional.ofNullable(itemCategoryDto)
                .map(ItemCategoryMap::mapItemCategoryDto)
                .orElse(null);
    }

    private static ItemCategory mapItemCategoryDto(ItemCategoryDto itemCategoryDto) {
        var itemCategory = new ItemCategory();

        itemCategory.setItemCategoryKey(itemCategoryDto.getItemCategoryKey());
        itemCategory.setName(itemCategoryDto.getName());

        return itemCategory;
    }


    // Model -> Entity

    // ItemCategory
    public static ItemCategoryEntity map(ItemCategory itemCategory) {
        return Optional.ofNullable(itemCategory)
                .map(ItemCategoryMap::mapItemCategory)
                .orElse(null);
    }

    private static ItemCategoryEntity mapItemCategory(ItemCategory itemCategory) {
        var itemCategoryEntity = new ItemCategoryEntity();

        itemCategoryEntity.setItemCategoryKey(itemCategory.getItemCategoryKey());
        itemCategoryEntity.setName(itemCategory.getName());
//        itemCategoryEntity.setItemCategories(MapUtil.map(itemCategory.getItemCategories(), itemCategoryEntity, ItemMap::map));

        return itemCategoryEntity;
    }
}
