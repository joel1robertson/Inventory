package org.joro.inventory.jpamodel.dto;

public interface ItemLinkDto {

    Long getItemLinkKey();

    ItemKeyDto getItem();

    LinkCategoryDto getLinkCategory();

    String getCaption();

    String getHref();

    String getSequenceIndex();
}
