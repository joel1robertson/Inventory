package org.joro.inventory.jpa.client;

import org.joro.inventory.jpa.model.projection.MarketplaceProjection;
import org.joro.inventory.jpa.model.entity.MarketplaceEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MarketplaceRepository extends LongKeyRepository<MarketplaceEntity> {

    List<MarketplaceProjection> findAll(Sort sort);

    MarketplaceProjection findByMarketplaceKey(Long marketplaceKey);

    MarketplaceProjection saveAndFlush(MarketplaceEntity marketplaceEntity);

    void delete(MarketplaceEntity marketplaceEntity);
}
