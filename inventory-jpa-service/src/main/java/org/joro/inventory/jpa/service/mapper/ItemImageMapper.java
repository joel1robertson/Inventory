package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemImageProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.jpa.model.entity.ItemImageEntity;
import org.joro.inventory.ui.model.data.ItemImage;

import java.util.Optional;

public class ItemImageMapper {

    private ItemImageMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemImageProjection
    public static ItemImage map(ItemImageProjection itemImageProjection) {
        return Optional.ofNullable(itemImageProjection)
                .map(ItemImageMapper::mapItemImageProjection)
                .orElse(null);
    }

    private static ItemImage mapItemImageProjection(ItemImageProjection itemImageProjection) {
        var itemImage = new ItemImage();

        itemImage.setItemImageKey(itemImageProjection.getItemImageKey());
        itemImage.setItem(ItemMapper.map(itemImageProjection.getItem()));
        itemImage.setCaption(itemImageProjection.getCaption());
        itemImage.setContentType(itemImageProjection.getContentType());
        itemImage.setImageData(itemImageProjection.getImageData());
        itemImage.setSequenceIndex(itemImageProjection.getSequenceIndex());

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
                .map(ItemImageMapper::mapItemImage)
                .orElse(null);
    }

    private static ItemImageEntity mapItemImage(ItemImage itemImage) {
        return mapItemImage(itemImage, ItemMapper.map(itemImage.getItem()));
    }
}
