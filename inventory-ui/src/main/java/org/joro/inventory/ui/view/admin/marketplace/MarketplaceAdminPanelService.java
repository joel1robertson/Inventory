package org.joro.inventory.ui.view.admin.marketplace;

import org.joro.inventory.ui.model.data.Marketplace;
import org.joro.inventory.service.MarketplaceService;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;

import java.util.List;

public class MarketplaceAdminPanelService implements AdminPanelService<Marketplace, Marketplace> {

    private final MarketplaceService marketplaceService;

    public MarketplaceAdminPanelService(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @Override
    public List<Marketplace> fetchSummaries() {
        return marketplaceService.fetchAll();
    }

    @Override
    public Marketplace fetchSummaryFromDetail(Marketplace marketplace) {
        return marketplace;
    }

    @Override
    public Marketplace fetchDetailFromSummary(Marketplace marketplace) {
        return marketplace;
    }

    @Override
    public Marketplace createDetail() {
        return new Marketplace();
    }

    @Override
    public Marketplace saveDetail(Marketplace marketplace) {
        return marketplaceService.save(marketplace);
    }

    @Override
    public void remove(Marketplace marketplace) {
        marketplaceService.remove(marketplace);
    }
}
