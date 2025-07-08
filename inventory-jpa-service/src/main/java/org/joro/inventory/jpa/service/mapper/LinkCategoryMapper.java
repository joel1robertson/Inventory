package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.LinkCategoryProjection;
import org.joro.inventory.jpa.model.entity.LinkCategoryEntity;
import org.joro.inventory.ui.model.data.LinkCategory;

import java.util.Optional;

public class LinkCategoryMapper {

    private LinkCategoryMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // LinkCategoryProjection
    public static LinkCategory map(LinkCategoryProjection linkCategoryProjection) {
        return Optional.ofNullable(linkCategoryProjection)
                .map(LinkCategoryMapper::mapLinkCategoryProjection)
                .orElse(null);
    }

    private static LinkCategory mapLinkCategoryProjection(LinkCategoryProjection linkCategoryProjection) {
        var linkCategory = new LinkCategory();

        linkCategory.setLinkCategoryKey(linkCategoryProjection.getLinkCategoryKey());
        linkCategory.setName(linkCategoryProjection.getName());

        return linkCategory;
    }


    // Model -> Entity

    // LinkCategory
    public static LinkCategoryEntity map(LinkCategory linkCategory) {
        return Optional.ofNullable(linkCategory)
                .map(LinkCategoryMapper::mapLinkCategory)
                .orElse(null);
    }

    private static LinkCategoryEntity mapLinkCategory(LinkCategory linkCategory) {
        var linkCategoryEntity = new LinkCategoryEntity();

        linkCategoryEntity.setLinkCategoryKey(linkCategory.getLinkCategoryKey());
        linkCategoryEntity.setName(linkCategory.getName());
//        linkCategoryEntity.setItemLinks(MapperUtil.map(linkCategory.getItemLinks(), linkCategoryEntity, ItemLinkMap::map));

        return linkCategoryEntity;
    }
}
