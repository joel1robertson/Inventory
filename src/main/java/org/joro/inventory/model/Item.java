package org.joro.inventory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Item {
    private Long itemKey;
    private ItemCategory itemCategory;
    private String name;
    private String manufacturerName;
    private String modelNumber;
    private Integer value;
    private Integer askingPrice;
    private Integer quantity;
    private String notes;
    private String title;
    private String description;
    private List<ItemLink> itemLinks;
    private List<ItemImage> itemImages;
    private List<ItemMarketplace> itemMarketplaces;
    private List<ItemProspect> itemProspects;
    private List<ItemSale> itemSales;

    public Integer getAvailable() {
        return Optional.ofNullable(quantity).orElse(1)
                - Optional.ofNullable(itemSales).map(List::size).orElse(0);
    }
}
