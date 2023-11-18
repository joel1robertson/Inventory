package org.joro.inventory.ui.view.admin.itemcategory;

import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.*;
import org.joro.inventory.model.ItemCategory;
import org.joro.inventory.service.ItemCategoryService;
import org.joro.inventory.ui.component.adminpanel.AdminPanel;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;

import java.util.Optional;
import java.util.function.Supplier;

import static org.joro.inventory.ui.view.admin.itemcategory.ItemCategoryAdminView.*;

@PageTitle("Item Category Admin")
@Route(value = ROUTE, layout = MainLayout.class)
public class ItemCategoryAdminView extends AdminPanel<ItemCategory, ItemCategory>
        implements HasUrlParameter<Long>, AfterNavigationObserver {
    public static final String ROUTE = "admin/itemcategory";

    public static void navigateTo() {
        RouteUtil.navigateTo(ItemCategoryAdminView.class);
    }

    public static void navigateTo(Long itemCategoryKey) {
        RouteUtil.navigateTo(ItemCategoryAdminView.class, itemCategoryKey);
    }

    private static void deepLinkTo(Long itemCategoryKey) {
        RouteUtil.deepLinkTo(ItemCategoryAdminView.class, itemCategoryKey);
    }

    private final transient ItemCategoryService itemCategoryService;
    private Long itemCategoryKey;

    public ItemCategoryAdminView(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;

        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long itemCategoryKey) {
        this.itemCategoryKey = itemCategoryKey;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populate();

        var itemCategory = Optional.ofNullable(itemCategoryKey).map(itemCategoryService::fetch).orElse(null);
        selectItem(itemCategory);
        if ((itemCategory == null) && (itemCategoryKey != null)) {
            onSummaryItemSelectionChange(null);
        }
    }

    @Override
    protected Supplier<AdminPanelService<ItemCategory, ItemCategory>> getAdminPanelServiceProvider() {
        return () -> new ItemCategoryAdminPanelService(itemCategoryService);
    }

    @Override
    protected String getSummaryColumnHeader() {
        return "Item Category";
    }

    @Override
    protected ValueProvider<ItemCategory, String> getSummaryItemValueProvider() {
        return ItemCategory::getName;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ItemCategoryAdminDetailPanel createAdminDetailPanel() {
        return new ItemCategoryAdminDetailPanel();
    }

    @Override
    protected void onSummaryItemSelectionChange(ItemCategory itemCategory) {
        deepLinkTo(Optional.ofNullable(itemCategory).map(ItemCategory::getItemCategoryKey).orElse(null));
    }
}
