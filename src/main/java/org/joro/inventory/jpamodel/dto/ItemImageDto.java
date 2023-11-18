package org.joro.inventory.jpamodel.dto;

public interface ItemImageDto {

    Long getItemImageKey();

    ItemKeyDto getItem();

    String getCaption();

    String getContentType();

    byte[] getImageData();

    String getSequenceIndex();
}
