package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemMarketplaceDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.jpamodel.entity.ItemMarketplaceEntity;
import org.joro.inventory.model.ItemMarketplace;

import java.util.Optional;

public class ItemMarketplaceMap {

    private ItemMarketplaceMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemMarketplaceDto
    public static ItemMarketplace map(ItemMarketplaceDto itemMarketplaceDto) {
        return Optional.ofNullable(itemMarketplaceDto)
                .map(ItemMarketplaceMap::mapItemMarketplaceDto)
                .orElse(null);
    }

    private static ItemMarketplace mapItemMarketplaceDto(ItemMarketplaceDto itemMarketplaceDto) {
        var itemMarketplace = new ItemMarketplace();

        itemMarketplace.setItemMarketplaceKey(itemMarketplaceDto.getItemMarketplaceKey());
        itemMarketplace.setItem(ItemMap.map(itemMarketplaceDto.getItem()));
        itemMarketplace.setMarketplace(MarketplaceMap.map(itemMarketplaceDto.getMarketplace()));
        itemMarketplace.setPostingUrl(itemMarketplaceDto.getPostingUrl());

        return itemMarketplace;
    }


    // Model -> Entity

    // ItemMarketplace
    public static ItemMarketplaceEntity map(ItemMarketplace itemMarketplace, ItemEntity itemEntity) {
        return Optional.ofNullable(itemMarketplace)
                .map(im -> mapItemMarketplace(im, itemEntity))
                .orElse(null);
    }

    private static ItemMarketplaceEntity mapItemMarketplace(ItemMarketplace itemMarketplace, ItemEntity itemEntity) {
        var itemMarketplaceEntity = new ItemMarketplaceEntity();

        itemMarketplaceEntity.setItemMarketplaceKey(itemMarketplace.getItemMarketplaceKey());
        itemMarketplaceEntity.setItem(itemEntity);
        itemMarketplaceEntity.setMarketplace(MarketplaceMap.map(itemMarketplace.getMarketplace()));
        itemMarketplaceEntity.setPostingUrl(itemMarketplace.getPostingUrl());

        return itemMarketplaceEntity;
    }

    public static ItemMarketplaceEntity map(ItemMarketplace itemMarketplace) {
        return Optional.ofNullable(itemMarketplace)
                .map(ItemMarketplaceMap::mapItemMarketplace)
                .orElse(null);
    }

    private static ItemMarketplaceEntity mapItemMarketplace(ItemMarketplace itemMarketplace) {
        return mapItemMarketplace(itemMarketplace, ItemMap.map(itemMarketplace.getItem()));
    }
}
