package org.joro.inventory.jpa.model.projection;

import java.time.LocalDate;

public interface ItemSaleProjection {

    Long getItemSaleKey();

    ItemKeyProjection getItem();

    LocalDate getSaleDate();

    Integer getSalePrice();

    String getBuyerName();
}
