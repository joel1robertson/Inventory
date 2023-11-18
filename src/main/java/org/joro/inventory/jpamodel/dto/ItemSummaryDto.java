package org.joro.inventory.jpamodel.dto;

import java.util.List;

public interface ItemSummaryDto extends ItemKeyDto {

    ItemCategoryDto getItemCategory();

    String getName();

    String getDescription();

    String getManufacturerName();

    String getModelNumber();

    Integer getValue();

    Integer getAskingPrice();

    Integer getQuantity();

    String getNotes();

    String getTitle();

    <T extends ItemSaleDto> List<T> getItemSales();

    <T extends ItemLinkDto> List<T> getItemLinks();

    <T extends ItemMarketplaceDto> List<T> getItemMarketplaces();

    <T extends ItemProspectDto> List<T> getItemProspects();
}
