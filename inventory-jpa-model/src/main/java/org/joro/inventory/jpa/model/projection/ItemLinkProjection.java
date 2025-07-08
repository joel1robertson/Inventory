package org.joro.inventory.jpa.model.projection;

public interface ItemLinkProjection {

    Long getItemLinkKey();

    ItemKeyProjection getItem();

    LinkCategoryProjection getLinkCategory();

    String getCaption();

    String getHref();

    String getSequenceIndex();
}
