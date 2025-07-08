package org.joro.inventory.jpa.model.projection;

public interface ItemProspectProjection {

    Long getItemProspectKey();

    ItemKeyProjection getItem();

    ProspectProjection getProspect();

    boolean isReserved();
}
