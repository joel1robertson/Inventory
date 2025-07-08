package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemMarketplaceProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.jpa.model.entity.ItemMarketplaceEntity;
import org.joro.inventory.ui.model.data.ItemMarketplace;

import java.util.Optional;

public class ItemMarketplaceMapper {

    private ItemMarketplaceMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemMarketplaceProjection
    public static ItemMarketplace map(ItemMarketplaceProjection itemMarketplaceProjection) {
        return Optional.ofNullable(itemMarketplaceProjection)
                .map(ItemMarketplaceMapper::mapItemMarketplaceProjection)
                .orElse(null);
    }

    private static ItemMarketplace mapItemMarketplaceProjection(ItemMarketplaceProjection itemMarketplaceProjection) {
        var itemMarketplace = new ItemMarketplace();

        itemMarketplace.setItemMarketplaceKey(itemMarketplaceProjection.getItemMarketplaceKey());
        itemMarketplace.setItem(ItemMapper.map(itemMarketplaceProjection.getItem()));
        itemMarketplace.setMarketplace(MarketplaceMapper.map(itemMarketplaceProjection.getMarketplace()));
        itemMarketplace.setPostingUrl(itemMarketplaceProjection.getPostingUrl());

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
        itemMarketplaceEntity.setMarketplace(MarketplaceMapper.map(itemMarketplace.getMarketplace()));
        itemMarketplaceEntity.setPostingUrl(itemMarketplace.getPostingUrl());

        return itemMarketplaceEntity;
    }

    public static ItemMarketplaceEntity map(ItemMarketplace itemMarketplace) {
        return Optional.ofNullable(itemMarketplace)
                .map(ItemMarketplaceMapper::mapItemMarketplace)
                .orElse(null);
    }

    private static ItemMarketplaceEntity mapItemMarketplace(ItemMarketplace itemMarketplace) {
        return mapItemMarketplace(itemMarketplace, ItemMapper.map(itemMarketplace.getItem()));
    }
}
