package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ItemProspectProjection;
import org.joro.inventory.jpa.model.entity.ItemEntity;
import org.joro.inventory.jpa.model.entity.ItemProspectEntity;
import org.joro.inventory.ui.model.data.ItemProspect;

import java.util.Optional;

public class ItemProspectMapper {

    private ItemProspectMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ItemProspectProjection
    public static ItemProspect map(ItemProspectProjection itemProspectProjection) {
        return Optional.ofNullable(itemProspectProjection)
                .map(ItemProspectMapper::mapItemProspectProjection)
                .orElse(null);
    }

    private static ItemProspect mapItemProspectProjection(ItemProspectProjection itemProspectProjection) {
        var itemProspect = new ItemProspect();

        itemProspect.setItemProspectKey(itemProspectProjection.getItemProspectKey());
        itemProspect.setItem(ItemMapper.map(itemProspectProjection.getItem()));
        itemProspect.setProspect(ProspectMapper.map(itemProspectProjection.getProspect()));
        itemProspect.setReserved(itemProspectProjection.isReserved());

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
        itemProspectEntity.setProspect(ProspectMapper.map(itemProspect.getProspect()));
        itemProspectEntity.setReserved(itemProspect.isReserved());

        return itemProspectEntity;
    }

    public static ItemProspectEntity map(ItemProspect itemProspect) {
        return Optional.ofNullable(itemProspect)
                .map(ItemProspectMapper::mapItemProspect)
                .orElse(null);
    }

    private static ItemProspectEntity mapItemProspect(ItemProspect itemProspect) {
        return mapItemProspect(itemProspect, ItemMapper.map(itemProspect.getItem()));
    }
}
