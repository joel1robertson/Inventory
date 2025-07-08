package org.joro.inventory.ui.view.admin.linkcategory;

import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.*;
import org.joro.inventory.ui.model.data.LinkCategory;
import org.joro.inventory.service.LinkCategoryService;
import org.joro.inventory.ui.component.adminpanel.AdminPanel;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;

import java.util.Optional;
import java.util.function.Supplier;

@PageTitle("Link Category Admin")
@Route(value = LinkCategoryAdminView.ROUTE, layout = MainLayout.class)
public class LinkCategoryAdminView extends AdminPanel<LinkCategory, LinkCategory>
        implements HasUrlParameter<Long>, AfterNavigationObserver {
    public static final String ROUTE = "admin/linkcategory";

    public static void navigateTo() {
        RouteUtil.navigateTo(LinkCategoryAdminView.class);
    }

    public static void navigateTo(Long linkCategoryKey) {
        RouteUtil.navigateTo(LinkCategoryAdminView.class, linkCategoryKey);
    }

    private static void deepLinkTo(Long linkCategoryKey) {
        RouteUtil.deepLinkTo(LinkCategoryAdminView.class, linkCategoryKey);
    }

    private final transient LinkCategoryService linkCategoryService;
    private Long linkCategoryKey;

    public LinkCategoryAdminView(LinkCategoryService linkCategoryService) {
        this.linkCategoryService = linkCategoryService;

        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long linkCategoryKey) {
        this.linkCategoryKey = linkCategoryKey;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populate();

        var linkCategory = Optional.ofNullable(linkCategoryKey).map(linkCategoryService::fetch).orElse(null);
        selectItem(linkCategory);
        if ((linkCategory == null) && (linkCategoryKey != null)) {
            onSummaryItemSelectionChange(null);
        }
    }

    @Override
    protected Supplier<AdminPanelService<LinkCategory, LinkCategory>> getAdminPanelServiceProvider() {
        return () -> new LinkCategoryAdminPanelService(linkCategoryService);
    }

    @Override
    protected String getSummaryColumnHeader() {
        return "Link Category";
    }

    @Override
    protected ValueProvider<LinkCategory, String> getSummaryItemValueProvider() {
        return LinkCategory::getName;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected LinkCategoryAdminDetailPanel createAdminDetailPanel() {
        return new LinkCategoryAdminDetailPanel();
    }

    @Override
    protected void onSummaryItemSelectionChange(LinkCategory linkCategory) {
        deepLinkTo(Optional.ofNullable(linkCategory).map(LinkCategory::getLinkCategoryKey).orElse(null));
    }
}
