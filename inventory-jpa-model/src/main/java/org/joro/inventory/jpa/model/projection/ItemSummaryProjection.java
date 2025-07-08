package org.joro.inventory.jpa.model.projection;

import java.util.List;

public interface ItemSummaryProjection extends ItemKeyProjection {

    ItemCategoryProjection getItemCategory();

    String getName();

    String getDescription();

    String getManufacturerName();

    String getModelNumber();

    Integer getValue();

    Integer getAskingPrice();

    Integer getQuantity();

    String getNotes();

    String getTitle();

    <T extends ItemSaleProjection> List<T> getItemSales();

    <T extends ItemLinkProjection> List<T> getItemLinks();

    <T extends ItemMarketplaceProjection> List<T> getItemMarketplaces();

    <T extends ItemProspectProjection> List<T> getItemProspects();
}
