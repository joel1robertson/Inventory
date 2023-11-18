package org.joro.inventory.jpaservice.map;

import org.joro.inventory.jpamodel.dto.ProspectDto;
import org.joro.inventory.jpamodel.entity.ProspectEntity;
import org.joro.inventory.model.Prospect;

import java.util.Optional;

public class ProspectMap {

    private ProspectMap() {
        // do not instantiate
    }


    // DTO -> Model

    // ProspectDto
    public static Prospect map(ProspectDto prospectDto) {
        return Optional.ofNullable(prospectDto)
                .map(ProspectMap::mapProspectDto)
                .orElse(null);
    }

    private static Prospect mapProspectDto(ProspectDto prospectDto) {
        var prospect = new Prospect();

        prospect.setProspectKey(prospectDto.getProspectKey());
        prospect.setName(prospectDto.getName());

        return prospect;
    }


    // Model -> Entity

    // Prospect
    public static ProspectEntity map(Prospect prospect) {
        return Optional.ofNullable(prospect)
                .map(ProspectMap::mapProspect)
                .orElse(null);
    }

    private static ProspectEntity mapProspect(Prospect prospect) {
        var prospectEntity = new ProspectEntity();

        prospectEntity.setProspectKey(prospect.getProspectKey());
        prospectEntity.setName(prospect.getName());
//        prospectEntity.setItemProspects(MapUtil.map(prospect.getItemProspects(), prospectEntity, ItemProspectMap::map));

        return prospectEntity;
    }
}
