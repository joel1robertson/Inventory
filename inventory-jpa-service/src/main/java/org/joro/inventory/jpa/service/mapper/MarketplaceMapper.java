package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.MarketplaceProjection;
import org.joro.inventory.jpa.model.entity.MarketplaceEntity;
import org.joro.inventory.ui.model.data.Marketplace;

import java.util.Optional;

public class MarketplaceMapper {

    private MarketplaceMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // MarketplaceProjection
    public static Marketplace map(MarketplaceProjection marketplaceProjection) {
        return Optional.ofNullable(marketplaceProjection)
                .map(MarketplaceMapper::mapMarketplaceProjection)
                .orElse(null);
    }

    private static Marketplace mapMarketplaceProjection(MarketplaceProjection marketplaceProjection) {
        var marketplace = new Marketplace();

        marketplace.setMarketplaceKey(marketplaceProjection.getMarketplaceKey());
        marketplace.setName(marketplaceProjection.getName());
        marketplace.setSiteUrl(marketplaceProjection.getSiteUrl());

        return marketplace;
    }


    // Model -> Entity

    // Marketplace
    public static MarketplaceEntity map(Marketplace marketplace) {
        return Optional.ofNullable(marketplace)
                .map(MarketplaceMapper::mapMarketplace)
                .orElse(null);
    }

    private static MarketplaceEntity mapMarketplace(Marketplace marketplace) {
        var marketplaceEntity = new MarketplaceEntity();

        marketplaceEntity.setMarketplaceKey(marketplace.getMarketplaceKey());
        marketplaceEntity.setName(marketplace.getName());
        marketplaceEntity.setSiteUrl(marketplace.getSiteUrl());
//        marketplaceEntity.setItemMarketplaces(MapperUtil.map(marketplace.getItemMarketplaces(), marketplaceEntity, ItemMarketplaceMap::map));

        return marketplaceEntity;
    }
}
