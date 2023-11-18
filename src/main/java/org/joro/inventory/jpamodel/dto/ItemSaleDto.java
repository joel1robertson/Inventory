package org.joro.inventory.jpamodel.dto;

import java.time.LocalDate;

public interface ItemSaleDto {

    Long getItemSaleKey();

    ItemKeyDto getItem();

    LocalDate getSaleDate();

    Integer getSalePrice();

    String getBuyerName();
}
