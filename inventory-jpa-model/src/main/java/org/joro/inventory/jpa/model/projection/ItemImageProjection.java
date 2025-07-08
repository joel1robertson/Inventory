package org.joro.inventory.jpa.model.projection;

public interface ItemImageProjection {

    Long getItemImageKey();

    ItemKeyProjection getItem();

    String getCaption();

    String getContentType();

    byte[] getImageData();

    String getSequenceIndex();
}
