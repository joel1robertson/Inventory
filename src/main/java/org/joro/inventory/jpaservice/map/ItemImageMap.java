package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemImageDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.jpamodel.entity.ItemImageEntity;
import org.joro.inventory.model.ItemImage;

import java.util.Optional;

public class ItemImageMap {

    private ItemImageMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemImageDto
    public static ItemImage map(ItemImageDto itemImageDto) {
        return Optional.ofNullable(itemImageDto)
                .map(ItemImageMap::mapItemImageDto)
                .orElse(null);
    }

    private static ItemImage mapItemImageDto(ItemImageDto itemImageDto) {
        var itemImage = new ItemImage();

        itemImage.setItemImageKey(itemImageDto.getItemImageKey());
        itemImage.setItem(ItemMap.map(itemImageDto.getItem()));
        itemImage.setCaption(itemImageDto.getCaption());
        itemImage.setContentType(itemImageDto.getContentType());
        itemImage.setImageData(itemImageDto.getImageData());
        itemImage.setSequenceIndex(itemImageDto.getSequenceIndex());

        return itemImage;
    }


    // Model -> Entity

    // ItemImage
    public static ItemImageEntity map(ItemImage itemImage, ItemEntity itemEntity) {
        return Optional.ofNullable(itemImage)
                .map(ii -> mapItemImage(ii, itemEntity))
                .orElse(null);
    }

    private static ItemImageEntity mapItemImage(ItemImage itemImage, ItemEntity itemEntity) {
        var itemImageEntity = new ItemImageEntity();

        itemImageEntity.setItemImageKey(itemImage.getItemImageKey());
        itemImageEntity.setItem(itemEntity);
        itemImageEntity.setImageData(itemImage.getImageData());
        itemImageEntity.setCaption(itemImage.getCaption());
        itemImageEntity.setContentType(itemImage.getContentType());
        itemImageEntity.setImageData(itemImage.getImageData());
        itemImageEntity.setSequenceIndex(itemImage.getSequenceIndex());

        return itemImageEntity;
    }

    public static ItemImageEntity map(ItemImage itemImage) {
        return Optional.ofNullable(itemImage)
                .map(ItemImageMap::mapItemImage)
                .orElse(null);
    }

    private static ItemImageEntity mapItemImage(ItemImage itemImage) {
        return mapItemImage(itemImage, ItemMap.map(itemImage.getItem()));
    }
}
