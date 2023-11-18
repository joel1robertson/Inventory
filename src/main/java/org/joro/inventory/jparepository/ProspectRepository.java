package org.joro.inventory.jparepository;

import org.joro.inventory.jpamodel.dto.ProspectDto;
import org.joro.inventory.jpamodel.entity.ProspectEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProspectRepository extends LongKeyRepository<ProspectEntity> {

    List<ProspectDto> findAll(Sort sort);

    ProspectDto findByProspectKey(Long prospectKey);

    ProspectDto saveAndFlush(ProspectEntity prospectEntity);

    void delete(ProspectEntity prospectEntity);
}
