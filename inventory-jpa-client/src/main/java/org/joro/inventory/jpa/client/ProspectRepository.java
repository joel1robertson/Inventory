package org.joro.inventory.jpa.client;

import org.joro.inventory.jpa.model.projection.ProspectProjection;
import org.joro.inventory.jpa.model.entity.ProspectEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProspectRepository extends LongKeyRepository<ProspectEntity> {

    List<ProspectProjection> findAll(Sort sort);

    ProspectProjection findByProspectKey(Long prospectKey);

    ProspectProjection saveAndFlush(ProspectEntity prospectEntity);

    void delete(ProspectEntity prospectEntity);
}
