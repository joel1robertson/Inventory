package org.joro.inventory.jpamodel.dto;

public interface ItemProspectDto {

    Long getItemProspectKey();

    ItemKeyDto getItem();

    ProspectDto getProspect();

    boolean isReserved();
}
