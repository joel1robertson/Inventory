package org.joro.inventory.jpamodel.dto;

public interface ItemMarketplaceDto {

    Long getItemMarketplaceKey();

    ItemKeyDto getItem();

    MarketplaceDto getMarketplace();

    String getPostingUrl();
}
