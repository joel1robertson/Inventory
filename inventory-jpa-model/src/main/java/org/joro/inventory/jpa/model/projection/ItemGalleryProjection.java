package org.joro.inventory.jpa.model.projection;

import java.util.List;

public interface ItemGalleryProjection extends ItemKeyProjection {

    String getName();

    String getTitle();

    <T extends ItemImageProjection> List<T> getItemImages();
}
