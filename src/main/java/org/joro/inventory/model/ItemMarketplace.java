package org.joro.inventory.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemMarketplace implements HasItem {
    private Long itemMarketplaceKey;
    private Item item;
    private Marketplace marketplace;
    private String postingUrl;
}
