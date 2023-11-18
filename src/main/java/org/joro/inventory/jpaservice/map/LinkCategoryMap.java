package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.LinkCategoryDto;
import org.joro.inventory.jpamodel.entity.LinkCategoryEntity;
import org.joro.inventory.model.LinkCategory;

import java.util.Optional;

public class LinkCategoryMap {

    private LinkCategoryMap() {
        // do not instantiate
    }


    // DTO -> Model

    // LinkCategoryDto
    public static LinkCategory map(LinkCategoryDto linkCategoryDto) {
        return Optional.ofNullable(linkCategoryDto)
                .map(LinkCategoryMap::mapLinkCategoryDto)
                .orElse(null);
    }

    private static LinkCategory mapLinkCategoryDto(LinkCategoryDto linkCategoryDto) {
        var linkCategory = new LinkCategory();

        linkCategory.setLinkCategoryKey(linkCategoryDto.getLinkCategoryKey());
        linkCategory.setName(linkCategoryDto.getName());

        return linkCategory;
    }


    // Model -> Entity

    // LinkCategory
    public static LinkCategoryEntity map(LinkCategory linkCategory) {
        return Optional.ofNullable(linkCategory)
                .map(LinkCategoryMap::mapLinkCategory)
                .orElse(null);
    }

    private static LinkCategoryEntity mapLinkCategory(LinkCategory linkCategory) {
        var linkCategoryEntity = new LinkCategoryEntity();

        linkCategoryEntity.setLinkCategoryKey(linkCategory.getLinkCategoryKey());
        linkCategoryEntity.setName(linkCategory.getName());
//        linkCategoryEntity.setItemLinks(MapUtil.map(linkCategory.getItemLinks(), linkCategoryEntity, ItemLinkMap::map));

        return linkCategoryEntity;
    }
}
