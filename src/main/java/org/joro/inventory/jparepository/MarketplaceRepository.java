package org.joro.inventory.jparepository;

import org.joro.inventory.jpamodel.dto.MarketplaceDto;
import org.joro.inventory.jpamodel.entity.MarketplaceEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MarketplaceRepository extends LongKeyRepository<MarketplaceEntity> {

    List<MarketplaceDto> findAll(Sort sort);

    MarketplaceDto findByMarketplaceKey(Long marketplaceKey);

    MarketplaceDto saveAndFlush(MarketplaceEntity marketplaceEntity);

    void delete(MarketplaceEntity marketplaceEntity);
}
