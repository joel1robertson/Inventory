package org.joro.inventory.ui.view.admin.prospect;

import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.*;
import org.joro.inventory.model.Prospect;
import org.joro.inventory.service.ProspectService;
import org.joro.inventory.ui.component.adminpanel.AdminPanel;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;

import java.util.Optional;
import java.util.function.Supplier;

@PageTitle("Prospect Admin")
@Route(value = ProspectAdminView.ROUTE, layout = MainLayout.class)
public class ProspectAdminView extends AdminPanel<Prospect, Prospect>
        implements HasUrlParameter<Long>, AfterNavigationObserver {
    public static final String ROUTE = "admin/prospect";

    public static void navigateTo() {
        RouteUtil.navigateTo(ProspectAdminView.class);
    }

    public static void navigateTo(Long prospectKey) {
        RouteUtil.navigateTo(ProspectAdminView.class, prospectKey);
    }

    private static void deepLinkTo(Long prospectKey) {
        RouteUtil.deepLinkTo(ProspectAdminView.class, prospectKey);
    }

    private final transient ProspectService prospectService;
    private Long prospectKey;

    public ProspectAdminView(ProspectService prospectService) {
        this.prospectService = prospectService;

        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long prospectKey) {
        this.prospectKey = prospectKey;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populate();

        var prospect = Optional.ofNullable(prospectKey).map(prospectService::fetch).orElse(null);
        selectItem(prospect);
        if ((prospect == null) && (prospectKey != null)) {
            onSummaryItemSelectionChange(null);
        }
    }

    @Override
    protected Supplier<AdminPanelService<Prospect, Prospect>> getAdminPanelServiceProvider() {
        return () -> new ProspectAdminPanelService(prospectService);
    }

    @Override
    protected String getSummaryColumnHeader() {
        return "Prospect";
    }

    @Override
    protected ValueProvider<Prospect, String> getSummaryItemValueProvider() {
        return Prospect::getName;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ProspectAdminDetailPanel createAdminDetailPanel() {
        return new ProspectAdminDetailPanel();
    }

    @Override
    protected void onSummaryItemSelectionChange(Prospect prospect) {
        deepLinkTo(Optional.ofNullable(prospect).map(Prospect::getProspectKey).orElse(null));
    }
}
