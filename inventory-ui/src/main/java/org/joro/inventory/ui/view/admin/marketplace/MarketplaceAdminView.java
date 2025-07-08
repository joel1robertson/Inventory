package org.joro.inventory.ui.view.admin.marketplace;

import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.*;
import org.joro.inventory.ui.model.data.Marketplace;
import org.joro.inventory.service.MarketplaceService;
import org.joro.inventory.ui.component.adminpanel.AdminPanel;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;

import java.util.Optional;
import java.util.function.Supplier;

@PageTitle("Marketplace Admin")
@Route(value = MarketplaceAdminView.ROUTE, layout = MainLayout.class)
public class MarketplaceAdminView extends AdminPanel<Marketplace, Marketplace>
        implements HasUrlParameter<Long>, AfterNavigationObserver {
    public static final String ROUTE = "admin/marketplace";

    public static void navigateTo() {
        RouteUtil.navigateTo(MarketplaceAdminView.class);
    }

    public static void navigateTo(Long marketplaceKey) {
        RouteUtil.navigateTo(MarketplaceAdminView.class, marketplaceKey);
    }

    private static void deepLinkTo(Long marketplaceKey) {
        RouteUtil.deepLinkTo(MarketplaceAdminView.class, marketplaceKey);
    }

    private final transient MarketplaceService marketplaceService;
    private Long marketplaceKey;

    public MarketplaceAdminView(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;

        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long marketplaceKey) {
        this.marketplaceKey = marketplaceKey;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populate();

        var marketplace = Optional.ofNullable(marketplaceKey).map(marketplaceService::fetch).orElse(null);
        selectItem(marketplace);
        if ((marketplace == null) && (marketplaceKey != null)) {
            onSummaryItemSelectionChange(null);
        }
    }

    @Override
    protected Supplier<AdminPanelService<Marketplace, Marketplace>> getAdminPanelServiceProvider() {
        return () -> new MarketplaceAdminPanelService(marketplaceService);
    }

    @Override
    protected String getSummaryColumnHeader() {
        return "Marketplace";
    }

    @Override
    protected ValueProvider<Marketplace, String> getSummaryItemValueProvider() {
        return Marketplace::getName;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected MarketplaceAdminDetailPanel createAdminDetailPanel() {
        return new MarketplaceAdminDetailPanel();
    }

    @Override
    protected void onSummaryItemSelectionChange(Marketplace marketplace) {
        deepLinkTo(Optional.ofNullable(marketplace).map(Marketplace::getMarketplaceKey).orElse(null));
    }
}
