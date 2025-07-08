package org.joro.inventory.jpa.service.mapper;

import org.joro.inventory.jpa.model.projection.ProspectProjection;
import org.joro.inventory.jpa.model.entity.ProspectEntity;
import org.joro.inventory.ui.model.data.Prospect;

import java.util.Optional;

public class ProspectMapper {

    private ProspectMapper() {
        // do not instantiate
    }


    // Projection -> Model

    // ProspectProjection
    public static Prospect map(ProspectProjection prospectProjection) {
        return Optional.ofNullable(prospectProjection)
                .map(ProspectMapper::mapProspectProjection)
                .orElse(null);
    }

    private static Prospect mapProspectProjection(ProspectProjection prospectProjection) {
        var prospect = new Prospect();

        prospect.setProspectKey(prospectProjection.getProspectKey());
        prospect.setName(prospectProjection.getName());

        return prospect;
    }


    // Model -> Entity

    // Prospect
    public static ProspectEntity map(Prospect prospect) {
        return Optional.ofNullable(prospect)
                .map(ProspectMapper::mapProspect)
                .orElse(null);
    }

    private static ProspectEntity mapProspect(Prospect prospect) {
        var prospectEntity = new ProspectEntity();

        prospectEntity.setProspectKey(prospect.getProspectKey());
        prospectEntity.setName(prospect.getName());
//        prospectEntity.setItemProspects(MapperUtil.map(prospect.getItemProspects(), prospectEntity, ItemProspectMap::map));

        return prospectEntity;
    }
}
