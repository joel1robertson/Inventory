package org.joro.inventory.jpamodel.dto;

import java.util.List;

public interface ItemGalleryDto extends ItemKeyDto {

    String getName();

    String getTitle();

    <T extends ItemImageDto> List<T> getItemImages();
}
