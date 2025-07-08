package org.joro.inventory.jpa.service;

import org.joro.inventory.jpa.model.projection.MarketplaceProjection;
import org.joro.inventory.jpa.client.MarketplaceRepository;
import org.joro.inventory.jpa.service.mapper.MapperUtil;
import org.joro.inventory.jpa.service.mapper.MarketplaceMapper;
import org.joro.inventory.ui.model.data.Marketplace;
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
        var sortedByName = Sort.sort(MarketplaceProjection.class).by(MarketplaceProjection::getName);
        return MapperUtil.map(repository.findAll(sortedByName), MarketplaceMapper::map);
    }

    @Override
    public Marketplace fetch(Long marketplaceKey) {
        return MarketplaceMapper.map(repository.findByMarketplaceKey(marketplaceKey));
    }

    @Override
    public Marketplace save(Marketplace marketplace) {
        return MarketplaceMapper.map(repository.saveAndFlush(MarketplaceMapper.map(marketplace)));
    }

    @Override
    public void remove(Marketplace marketplace) {
        repository.delete(MarketplaceMapper.map(marketplace));
    }
}
