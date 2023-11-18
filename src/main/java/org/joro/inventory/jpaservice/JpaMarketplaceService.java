package org.joro.inventory.jpaservice;

import org.joro.inventory.jpamodel.dto.MarketplaceDto;
import org.joro.inventory.jparepository.MarketplaceRepository;
import org.joro.inventory.jpaservice.map.MapUtil;
import org.joro.inventory.jpaservice.map.MarketplaceMap;
import org.joro.inventory.model.Marketplace;
import org.joro.inventory.service.MarketplaceService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaMarketplaceService implements MarketplaceService {

    private final MarketplaceRepository repository;

    public JpaMarketplaceService(MarketplaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Marketplace> fetchAll() {
        var sortedByName = Sort.sort(MarketplaceDto.class).by(MarketplaceDto::getName);
        return MapUtil.map(repository.findAll(sortedByName), MarketplaceMap::map);
    }

    @Override
    public Marketplace fetch(Long marketplaceKey) {
        return MarketplaceMap.map(repository.findByMarketplaceKey(marketplaceKey));
    }

    @Override
    public Marketplace save(Marketplace marketplace) {
        return MarketplaceMap.map(repository.saveAndFlush(MarketplaceMap.map(marketplace)));
    }

    @Override
    public void remove(Marketplace marketplace) {
        repository.delete(MarketplaceMap.map(marketplace));
    }
}
