package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemLinkDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.jpamodel.entity.ItemLinkEntity;
import org.joro.inventory.model.ItemLink;

import java.util.Optional;

public class ItemLinkMap {

    private ItemLinkMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemLinkDto
    public static ItemLink map(ItemLinkDto itemLinkDto) {
        return Optional.ofNullable(itemLinkDto)
                .map(ItemLinkMap::mapItemLinkDto)
                .orElse(null);
    }

    private static ItemLink mapItemLinkDto(ItemLinkDto itemLinkDto) {
        var itemLink = new ItemLink();

        itemLink.setItemLinkKey(itemLinkDto.getItemLinkKey());
        itemLink.setItem(ItemMap.map(itemLinkDto.getItem()));
        itemLink.setLinkCategory(LinkCategoryMap.map(itemLinkDto.getLinkCategory()));
        itemLink.setCaption(itemLinkDto.getCaption());
        itemLink.setHref(itemLinkDto.getHref());
        itemLink.setSequenceIndex(itemLinkDto.getSequenceIndex());

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
        itemLinkEntity.setLinkCategory(LinkCategoryMap.map(itemLink.getLinkCategory()));
        itemLinkEntity.setCaption(itemLink.getCaption());
        itemLinkEntity.setHref(itemLink.getHref());
        itemLinkEntity.setSequenceIndex(itemLink.getSequenceIndex());

        return itemLinkEntity;
    }

    public static ItemLinkEntity map(ItemLink itemLink) {
        return Optional.ofNullable(itemLink)
                .map(ItemLinkMap::mapItemLink)
                .orElse(null);
    }

    private static ItemLinkEntity mapItemLink(ItemLink itemLink) {
        return mapItemLink(itemLink, ItemMap.map(itemLink.getItem()));
    }
}
