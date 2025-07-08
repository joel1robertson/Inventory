package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemSaleProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.jpa.model.entity.ItemSaleEntity;
import org.joro.inventory.ui.model.data.ItemSale;

import java.util.Optional;

public class ItemSaleMapper {

    private ItemSaleMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemSaleProjection
    public static ItemSale map(ItemSaleProjection itemSaleProjection) {
        return Optional.ofNullable(itemSaleProjection)
                .map(ItemSaleMapper::mapItemSaleProjection)
                .orElse(null);
    }

    private static ItemSale mapItemSaleProjection(ItemSaleProjection itemSaleProjection) {
        var itemSale = new ItemSale();

        itemSale.setItemSaleKey(itemSaleProjection.getItemSaleKey());
        itemSale.setItem(ItemMapper.map(itemSaleProjection.getItem()));
        itemSale.setSaleDate(itemSaleProjection.getSaleDate());
        itemSale.setSalePrice(itemSaleProjection.getSalePrice());
        itemSale.setBuyerName(itemSaleProjection.getBuyerName());

        return itemSale;
    }


    // Model -> Entity

    // ItemSale
    public static ItemSaleEntity map(ItemSale itemSale, ItemEntity itemEntity) {
        return Optional.ofNullable(itemSale)
                .map(is -> mapItemSale(is, itemEntity))
                .orElse(null);
    }

    private static ItemSaleEntity mapItemSale(ItemSale itemSale, ItemEntity itemEntity) {
        var itemSaleEntity = new ItemSaleEntity();

        itemSaleEntity.setItemSaleKey(itemSale.getItemSaleKey());
        itemSaleEntity.setItem(itemEntity);
        itemSaleEntity.setSaleDate(itemSale.getSaleDate());
        itemSaleEntity.setSalePrice(itemSale.getSalePrice());
        itemSaleEntity.setBuyerName(itemSale.getBuyerName());

        return itemSaleEntity;
    }

    public static ItemSaleEntity map(ItemSale itemSale) {
        return Optional.ofNullable(itemSale)
                .map(ItemSaleMapper::mapItemSale)
                .orElse(null);
    }

    private static ItemSaleEntity mapItemSale(ItemSale itemSale) {
        return mapItemSale(itemSale, ItemMapper.map(itemSale.getItem()));
    }
}
