package org.joro.inventory.service;

import org.joro.inventory.ui.model.data.Marketplace;

import java.util.List;

public interface MarketplaceService {

    List<Marketplace> fetchAll();

    Marketplace fetch(Long marketplaceKey);

    Marketplace save(Marketplace marketplace);

    void remove(Marketplace marketplace);
}
