package org.joro.inventory.jpa.model.projection;

public interface ItemMarketplaceProjection {

    Long getItemMarketplaceKey();

    ItemKeyProjection getItem();

    MarketplaceProjection getMarketplace();

    String getPostingUrl();
}
