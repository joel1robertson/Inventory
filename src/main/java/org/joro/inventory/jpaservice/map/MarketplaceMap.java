package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.MarketplaceDto;
import org.joro.inventory.jpamodel.entity.MarketplaceEntity;
import org.joro.inventory.model.Marketplace;

import java.util.Optional;

public class MarketplaceMap {

    private MarketplaceMap() {
        // do not instantiate
    }


    // DTO -> Model

    // MarketplaceDto
    public static Marketplace map(MarketplaceDto marketplaceDto) {
        return Optional.ofNullable(marketplaceDto)
                .map(MarketplaceMap::mapMarketplaceDto)
                .orElse(null);
    }

    private static Marketplace mapMarketplaceDto(MarketplaceDto marketplaceDto) {
        var marketplace = new Marketplace();

        marketplace.setMarketplaceKey(marketplaceDto.getMarketplaceKey());
        marketplace.setName(marketplaceDto.getName());
        marketplace.setSiteUrl(marketplaceDto.getSiteUrl());

        return marketplace;
    }


    // Model -> Entity

    // Marketplace
    public static MarketplaceEntity map(Marketplace marketplace) {
        return Optional.ofNullable(marketplace)
                .map(MarketplaceMap::mapMarketplace)
                .orElse(null);
    }

    private static MarketplaceEntity mapMarketplace(Marketplace marketplace) {
        var marketplaceEntity = new MarketplaceEntity();

        marketplaceEntity.setMarketplaceKey(marketplace.getMarketplaceKey());
        marketplaceEntity.setName(marketplace.getName());
        marketplaceEntity.setSiteUrl(marketplace.getSiteUrl());
//        marketplaceEntity.setItemMarketplaces(MapUtil.map(marketplace.getItemMarketplaces(), marketplaceEntity, ItemMarketplaceMap::map));

        return marketplaceEntity;
    }
}
