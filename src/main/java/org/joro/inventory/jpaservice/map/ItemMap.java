package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemDetailDto;
import org.joro.inventory.jpamodel.dto.ItemGalleryDto;
import org.joro.inventory.jpamodel.dto.ItemKeyDto;
import org.joro.inventory.jpamodel.dto.ItemSummaryDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.model.Item;

import java.util.Optional;

public class ItemMap {

    private ItemMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemKeyDto
    public static Item map(ItemKeyDto itemKeyDto) {
        return Optional.ofNullable(itemKeyDto)
                .map(ItemMap::mapItemKeyDto)
                .orElse(null);
    }

    private static Item mapItemKeyDto(ItemKeyDto itemKeyDto) {
        var item = new Item();

        item.setItemKey(itemKeyDto.getItemKey());

        return item;
    }

    // ItemSummaryDto
    public static Item map(ItemSummaryDto itemSummaryDto) {
        return Optional.ofNullable(itemSummaryDto)
                .map(ItemMap::mapItemSummaryDto)
                .orElse(null);
    }

    private static Item mapItemSummaryDto(ItemSummaryDto itemSummaryDto) {
        var item = mapItemKeyDto(itemSummaryDto);

        item.setItemCategory(ItemCategoryMap.map(itemSummaryDto.getItemCategory()));
        item.setName(itemSummaryDto.getName());
        item.setManufacturerName(itemSummaryDto.getManufacturerName());
        item.setModelNumber(itemSummaryDto.getModelNumber());
        item.setValue(itemSummaryDto.getValue());
        item.setAskingPrice(itemSummaryDto.getAskingPrice());
        item.setQuantity(itemSummaryDto.getQuantity());
        item.setItemSales(MapUtil.map(itemSummaryDto.getItemSales(), ItemSaleMap::map));

        item.setQuantity(itemSummaryDto.getQuantity());
        item.setNotes(itemSummaryDto.getNotes());
        item.setTitle(itemSummaryDto.getTitle());
        item.setDescription(itemSummaryDto.getDescription());
        item.setItemLinks(MapUtil.map(itemSummaryDto.getItemLinks(), ItemLinkMap::map));
        item.setItemMarketplaces(MapUtil.map(itemSummaryDto.getItemMarketplaces(), ItemMarketplaceMap::map));
        item.setItemProspects(MapUtil.map(itemSummaryDto.getItemProspects(), ItemProspectMap::map));

        return item;
    }

    // ItemGalleryDto
    public static Item map(ItemGalleryDto itemGalleryDto) {
        return Optional.ofNullable(itemGalleryDto)
                .map(ItemMap::mapItemGalleryDto)
                .orElse(null);
    }

    private static Item mapItemGalleryDto(ItemGalleryDto itemGalleryDto) {
        var item = mapItemKeyDto(itemGalleryDto);

        item.setName(itemGalleryDto.getName());
        item.setTitle(itemGalleryDto.getTitle());
        item.setItemImages(MapUtil.map(itemGalleryDto.getItemImages(), ItemImageMap::map));

        return item;
    }

    // ItemDetailDto
    public static Item map(ItemDetailDto itemDetailDto) {
        return Optional.ofNullable(itemDetailDto)
                .map(ItemMap::mapItemDetailDto)
                .orElse(null);
    }

    private static Item mapItemDetailDto(ItemDetailDto itemDetailDto) {
        var item = mapItemSummaryDto(itemDetailDto);

        item.setItemImages(MapUtil.map(itemDetailDto.getItemImages(), ItemImageMap::map));

        return item;
    }

//    // ItemEntity
//    public static Item map(ItemEntity itemEntity) {
//        return Optional.ofNullable(itemEntity)
//                .map(ItemMap::mapItemEntity)
//                .orElse(null);
//    }
//
//    private static Item mapItemEntity(ItemEntity itemEntity) {
//        return mapItemDetailDto(itemEntity);
//    }


    // Model -> Entity

    // Item
    public static ItemEntity map(Item item) {
        return Optional.ofNullable(item)
                .map(ItemMap::mapItem)
                .orElse(null);
    }

    private static ItemEntity mapItem(Item item) {
        var itemEntity = new ItemEntity();

        itemEntity.setItemKey(item.getItemKey());
        itemEntity.setItemCategory(ItemCategoryMap.map(item.getItemCategory()));
        itemEntity.setName(item.getName());
        itemEntity.setManufacturerName(item.getManufacturerName());
        itemEntity.setModelNumber(item.getModelNumber());
        itemEntity.setValue(item.getValue());
        itemEntity.setAskingPrice(item.getAskingPrice());
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setNotes(item.getNotes());
        itemEntity.setTitle(item.getTitle());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setItemLinks(MapUtil.map(item.getItemLinks(), itemEntity, ItemLinkMap::map));
        itemEntity.setItemImages(MapUtil.map(item.getItemImages(), itemEntity, ItemImageMap::map));
        itemEntity.setItemMarketplaces(MapUtil.map(item.getItemMarketplaces(), itemEntity, ItemMarketplaceMap::map));
        itemEntity.setItemProspects(MapUtil.map(item.getItemProspects(), itemEntity, ItemProspectMap::map));
        itemEntity.setItemSales(MapUtil.map(item.getItemSales(), itemEntity, ItemSaleMap::map));

        return itemEntity;
    }
}
