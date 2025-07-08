package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemDetailProjection;
import org.joro.inventory.jpa.model.projection.ItemGalleryProjection;
import org.joro.inventory.jpa.model.projection.ItemKeyProjection;
import org.joro.inventory.jpa.model.projection.ItemSummaryProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.ui.model.data.Item;

import java.util.Optional;

public class ItemMapper {

    private ItemMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemKeyProjection
    public static Item map(ItemKeyProjection itemKeyProjection) {
        return Optional.ofNullable(itemKeyProjection)
                .map(ItemMapper::mapItemKeyProjection)
                .orElse(null);
    }

    private static Item mapItemKeyProjection(ItemKeyProjection itemKeyProjection) {
        var item = new Item();

        item.setItemKey(itemKeyProjection.getItemKey());

        return item;
    }

    // ItemSummaryProjection
    public static Item map(ItemSummaryProjection itemSummaryProjection) {
        return Optional.ofNullable(itemSummaryProjection)
                .map(ItemMapper::mapItemSummaryProjection)
                .orElse(null);
    }

    private static Item mapItemSummaryProjection(ItemSummaryProjection itemSummaryProjection) {
        var item = mapItemKeyProjection(itemSummaryProjection);

        item.setItemCategory(ItemCategoryMapper.map(itemSummaryProjection.getItemCategory()));
        item.setName(itemSummaryProjection.getName());
        item.setManufacturerName(itemSummaryProjection.getManufacturerName());
        item.setModelNumber(itemSummaryProjection.getModelNumber());
        item.setValue(itemSummaryProjection.getValue());
        item.setAskingPrice(itemSummaryProjection.getAskingPrice());
        item.setQuantity(itemSummaryProjection.getQuantity());
        item.setItemSales(MapperUtil.map(itemSummaryProjection.getItemSales(), ItemSaleMapper::map));

        item.setQuantity(itemSummaryProjection.getQuantity());
        item.setNotes(itemSummaryProjection.getNotes());
        item.setTitle(itemSummaryProjection.getTitle());
        item.setDescription(itemSummaryProjection.getDescription());
        item.setItemLinks(MapperUtil.map(itemSummaryProjection.getItemLinks(), ItemLinkMapper::map));
        item.setItemMarketplaces(MapperUtil.map(itemSummaryProjection.getItemMarketplaces(), ItemMarketplaceMapper::map));
        item.setItemProspects(MapperUtil.map(itemSummaryProjection.getItemProspects(), ItemProspectMapper::map));

        return item;
    }

    // ItemGalleryProjection
    public static Item map(ItemGalleryProjection itemGalleryProjection) {
        return Optional.ofNullable(itemGalleryProjection)
                .map(ItemMapper::mapItemGalleryProjection)
                .orElse(null);
    }

    private static Item mapItemGalleryProjection(ItemGalleryProjection itemGalleryProjection) {
        var item = mapItemKeyProjection(itemGalleryProjection);

        item.setName(itemGalleryProjection.getName());
        item.setTitle(itemGalleryProjection.getTitle());
        item.setItemImages(MapperUtil.map(itemGalleryProjection.getItemImages(), ItemImageMapper::map));

        return item;
    }

    // ItemDetailProjection
    public static Item map(ItemDetailProjection itemDetailProjection) {
        return Optional.ofNullable(itemDetailProjection)
                .map(ItemMapper::mapItemDetailProjection)
                .orElse(null);
    }

    private static Item mapItemDetailProjection(ItemDetailProjection itemDetailProjection) {
        var item = mapItemSummaryProjection(itemDetailProjection);

        item.setItemImages(MapperUtil.map(itemDetailProjection.getItemImages(), ItemImageMapper::map));

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
//        return mapItemDetailProjection(itemEntity);
//    }


    // Model -> Entity

    // Item
    public static ItemEntity map(Item item) {
        return Optional.ofNullable(item)
                .map(ItemMapper::mapItem)
                .orElse(null);
    }

    private static ItemEntity mapItem(Item item) {
        var itemEntity = new ItemEntity();

        itemEntity.setItemKey(item.getItemKey());
        itemEntity.setItemCategory(ItemCategoryMapper.map(item.getItemCategory()));
        itemEntity.setName(item.getName());
        itemEntity.setManufacturerName(item.getManufacturerName());
        itemEntity.setModelNumber(item.getModelNumber());
        itemEntity.setValue(item.getValue());
        itemEntity.setAskingPrice(item.getAskingPrice());
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setNotes(item.getNotes());
        itemEntity.setTitle(item.getTitle());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setItemLinks(MapperUtil.map(item.getItemLinks(), itemEntity, ItemLinkMapper::map));
        itemEntity.setItemImages(MapperUtil.map(item.getItemImages(), itemEntity, ItemImageMapper::map));
        itemEntity.setItemMarketplaces(MapperUtil.map(item.getItemMarketplaces(), itemEntity, ItemMarketplaceMapper::map));
        itemEntity.setItemProspects(MapperUtil.map(item.getItemProspects(), itemEntity, ItemProspectMapper::map));
        itemEntity.setItemSales(MapperUtil.map(item.getItemSales(), itemEntity, ItemSaleMapper::map));

        return itemEntity;
    }
}
