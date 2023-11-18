package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ItemProspectDto;
import org.joro.inventory.jpamodel.entity.ItemEntity;
import org.joro.inventory.jpamodel.entity.ItemProspectEntity;
import org.joro.inventory.model.ItemProspect;

import java.util.Optional;

public class ItemProspectMap {

    private ItemProspectMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ItemProspectDto
    public static ItemProspect map(ItemProspectDto itemProspectDto) {
        return Optional.ofNullable(itemProspectDto)
                .map(ItemProspectMap::mapItemProspectDto)
                .orElse(null);
    }

    private static ItemProspect mapItemProspectDto(ItemProspectDto itemProspectDto) {
        var itemProspect = new ItemProspect();

        itemProspect.setItemProspectKey(itemProspectDto.getItemProspectKey());
        itemProspect.setItem(ItemMap.map(itemProspectDto.getItem()));
        itemProspect.setProspect(ProspectMap.map(itemProspectDto.getProspect()));
        itemProspect.setReserved(itemProspectDto.isReserved());

        return itemProspect;
    }


    // Model -> Entity

    // ItemProspect
    public static ItemProspectEntity map(ItemProspect itemProspect, ItemEntity itemEntity) {
        return Optional.ofNullable(itemProspect)
                .map(ip -> mapItemProspect(ip, itemEntity))
                .orElse(null);
    }

    private static ItemProspectEntity mapItemProspect(ItemProspect itemProspect, ItemEntity itemEntity) {
        var itemProspectEntity = new ItemProspectEntity();

        itemProspectEntity.setItemProspectKey(itemProspect.getItemProspectKey());
        itemProspectEntity.setItem(itemEntity);
        itemProspectEntity.setProspect(ProspectMap.map(itemProspect.getProspect()));
        itemProspectEntity.setReserved(itemProspect.isReserved());

        return itemProspectEntity;
    }

    public static ItemProspectEntity map(ItemProspect itemProspect) {
        return Optional.ofNullable(itemProspect)
                .map(ItemProspectMap::mapItemProspect)
                .orElse(null);
    }

    private static ItemProspectEntity mapItemProspect(ItemProspect itemProspect) {
        return mapItemProspect(itemProspect, ItemMap.map(itemProspect.getItem()));
    }
}
