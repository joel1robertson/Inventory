package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemLinkProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.jpa.model.entity.ItemLinkEntity;
import org.joro.inventory.ui.model.data.ItemLink;

import java.util.Optional;

public class ItemLinkMapper {

    private ItemLinkMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemLinkProjection
    public static ItemLink map(ItemLinkProjection itemLinkProjection) {
        return Optional.ofNullable(itemLinkProjection)
                .map(ItemLinkMapper::mapItemLinkProjection)
                .orElse(null);
    }

    private static ItemLink mapItemLinkProjection(ItemLinkProjection itemLinkProjection) {
        var itemLink = new ItemLink();

        itemLink.setItemLinkKey(itemLinkProjection.getItemLinkKey());
        itemLink.setItem(ItemMapper.map(itemLinkProjection.getItem()));
        itemLink.setLinkCategory(LinkCategoryMapper.map(itemLinkProjection.getLinkCategory()));
        itemLink.setCaption(itemLinkProjection.getCaption());
        itemLink.setHref(itemLinkProjection.getHref());
        itemLink.setSequenceIndex(itemLinkProjection.getSequenceIndex());

        return itemLink;
    }


    // Model -> Entity

    // ItemLink
    public static ItemLinkEntity map(ItemLink itemLink, ItemEntity itemEntity) {
        return Optional.ofNullable(itemLink)
                .map(il -> mapItemLink(il, itemEntity))
                .orElse(null);
    }

    private static ItemLinkEntity mapItemLink(ItemLink itemLink, ItemEntity itemEntity) {
        var itemLinkEntity = new ItemLinkEntity();

        itemLinkEntity.setItemLinkKey(itemLink.getItemLinkKey());
        itemLinkEntity.setItem(itemEntity);
        itemLinkEntity.setLinkCategory(LinkCategoryMapper.map(itemLink.getLinkCategory()));
        itemLinkEntity.setCaption(itemLink.getCaption());
        itemLinkEntity.setHref(itemLink.getHref());
        itemLinkEntity.setSequenceIndex(itemLink.getSequenceIndex());

        return itemLinkEntity;
    }

    public static ItemLinkEntity map(ItemLink itemLink) {
        return Optional.ofNullable(itemLink)
                .map(ItemLinkMapper::mapItemLink)
                .orElse(null);
    }

    private static ItemLinkEntity mapItemLink(ItemLink itemLink) {
        return mapItemLink(itemLink, ItemMapper.map(itemLink.getItem()));
    }
}
