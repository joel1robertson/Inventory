package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemSaleDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.jpamodel.entity.ItemSaleEntity;
import org.joro.inventory.model.ItemSale;

import java.util.Optional;

public class ItemSaleMap {

    private ItemSaleMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemSaleDto
    public static ItemSale map(ItemSaleDto itemSaleDto) {
        return Optional.ofNullable(itemSaleDto)
                .map(ItemSaleMap::mapItemSaleDto)
                .orElse(null);
    }

    private static ItemSale mapItemSaleDto(ItemSaleDto itemSaleDto) {
        var itemSale = new ItemSale();

        itemSale.setItemSaleKey(itemSaleDto.getItemSaleKey());
        itemSale.setItem(ItemMap.map(itemSaleDto.getItem()));
        itemSale.setSaleDate(itemSaleDto.getSaleDate());
        itemSale.setSalePrice(itemSaleDto.getSalePrice());
        itemSale.setBuyerName(itemSaleDto.getBuyerName());

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
                .map(ItemSaleMap::mapItemSale)
                .orElse(null);
    }

    private static ItemSaleEntity mapItemSale(ItemSale itemSale) {
        return mapItemSale(itemSale, ItemMap.map(itemSale.getItem()));
    }
}
