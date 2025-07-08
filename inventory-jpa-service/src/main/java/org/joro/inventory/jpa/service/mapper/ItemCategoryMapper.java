package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemCategoryProjection;
import org.joro.inventory.jpa.model.entity.ItemCategoryEntity;
import org.joro.inventory.ui.model.data.ItemCategory;

import java.util.Optional;

public class ItemCategoryMapper {

    private ItemCategoryMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemCategoryProjection
    public static ItemCategory map(ItemCategoryProjection itemCategoryProjection) {
        return Optional.ofNullable(itemCategoryProjection)
                .map(ItemCategoryMapper::mapItemCategoryProjection)
                .orElse(null);
    }

    private static ItemCategory mapItemCategoryProjection(ItemCategoryProjection itemCategoryProjection) {
        var itemCategory = new ItemCategory();

        itemCategory.setItemCategoryKey(itemCategoryProjection.getItemCategoryKey());
        itemCategory.setName(itemCategoryProjection.getName());

        return itemCategory;
    }


    // Model -> Entity

    // ItemCategory
    public static ItemCategoryEntity map(ItemCategory itemCategory) {
        return Optional.ofNullable(itemCategory)
                .map(ItemCategoryMapper::mapItemCategory)
                .orElse(null);
    }

    private static ItemCategoryEntity mapItemCategory(ItemCategory itemCategory) {
        var itemCategoryEntity = new ItemCategoryEntity();

        itemCategoryEntity.setItemCategoryKey(itemCategory.getItemCategoryKey());
        itemCategoryEntity.setName(itemCategory.getName());
//        itemCategoryEntity.setItemCategories(MapperUtil.map(itemCategory.getItemCategories(), itemCategoryEntity, ItemMap::map));

        return itemCategoryEntity;
    }
}
