package org.joro.inventory.service;

import org.joro.inventory.model.Marketplace;

import java.util.List;

public interface MarketplaceService {

    List<Marketplace> fetchAll();

    Marketplace fetch(Long marketplaceKey);

    Marketplace save(Marketplace marketplace);

    void remove(Marketplace marketplace);
}
