package org.joro.inventory.ui.view.itemreport;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.joro.inventory.cache.SessionCache;
import org.joro.inventory.ui.model.data.Item;
import org.joro.inventory.ui.model.data.ItemCategory;
import org.joro.inventory.ui.model.data.ItemImage;
import org.joro.inventory.ui.model.data.ItemLink;
import org.joro.inventory.ui.util.GridUtil;
import org.joro.inventory.ui.util.RouteUtil;

import java.text.NumberFormat;
import java.util.Optional;

/**
 *
 * <pre>
 * +-content(VerticalLayout)---------------------------------------------------------------+
 * | +-headerBar(VerticalLayout)---------------------------------------------------------+ |
 * | | Farm Equipment & Household Item Liquidation Sale                                  | |
 * | | Contact: Joel Robertson, 10527 Moller Rd, Spearfish, SD +1 (317) 443-5516         | |
 * | +-----------------------------------------------------------------------------------+ |
 * | +-itemGrid--------------------------------------------------------------------------+ |
 * | |  Item              | Make      | Model     | Type      | Worth | Ask | Available  | |
 * | | ------------------- ----------- ----------- ----------- ------- ----- ----------- | |
 * | |  Cooler              Coleman     120qt       Rec           $35   $25      3 of 4  | |
 * | +-----------------------------------------------------------------------------------+ |
 * +---------------------------------------------------------------------------------------+
 * </pre>
 */
@PageTitle("Items")
@Route(value = ItemReportView.ROUTE)
public class ItemReportView extends Composite<VerticalLayout> implements AfterNavigationObserver {
    public static final String ROUTE = "report";
    public static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();

    public static void navigateTo() {
        RouteUtil.open(ItemReportView.class);
    }

    private final transient SessionCache sessionCache;

    private final Grid<Item> itemGrid;

    public ItemReportView(SessionCache sessionCache) {
        this.sessionCache = sessionCache;

        addClassNames("item-report-view");

        var titleSpan = new Span("Farm Equipment & Household Item Liquidation Sale");
        titleSpan.addClassNames(LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.EXTRABOLD);

        var enticementSpan = new Span("Prices already slashed to sell quickly, but make me an offer I can't refuse!");
        enticementSpan.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        enticementSpan.getStyle().set("font-style", "italic");

        var contactSpan = new Span("Contact: Joel Robertson | 10527 Moller Rd, Spearfish, SD | +1 (317) 443-5516");
        contactSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);

        // header bar
        var headerBar = new VerticalLayout();
        headerBar.setAlignItems(FlexComponent.Alignment.CENTER);
        headerBar.add(titleSpan);
        headerBar.add(enticementSpan);
        headerBar.add(contactSpan);

        // item grid
        itemGrid = new Grid<>();
        itemGrid.setAllRowsVisible(true);
        itemGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        itemGrid.addColumn(GridUtil.imageRenderer(GridUtil.propertyOf(GridUtil.indexOf(Item::getItemImages, 0), ItemImage::getImageData),
                        GridUtil.propertyOf(GridUtil.indexOf(Item::getItemImages, 0), ItemImage::getContentType)))
                .setHeader("Image")
                .setWidth("8em")
                .setFlexGrow(0);
        itemGrid.addColumn(getGetTitle())
                .setHeader("Item")
                .setFlexGrow(6);
        itemGrid.addColumn(GridUtil.propertyOf(Item::getItemCategory, ItemCategory::getName))
                .setHeader("Type")
                .setFlexGrow(1);
        itemGrid.addColumn(GridUtil.urlRenderer(GridUtil.propertyOf(GridUtil.indexOf(Item::getItemLinks, 0), ItemLink::getHref),
                        GridUtil.propertyOf(GridUtil.indexOf(Item::getItemLinks, 0), ItemLink::getCaption)))
                .setHeader("Link")
                .setFlexGrow(1);
        itemGrid.addColumn(new NumberRenderer<>(Item::getValue, CURRENCY_FORMATTER))
                .setHeader("Worth")
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("9em")
                .setFlexGrow(0);
        itemGrid.addColumn(new NumberRenderer<>(Item::getAskingPrice, CURRENCY_FORMATTER))
                .setHeader("Asking")
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("9em")
                .setFlexGrow(0);
        itemGrid.addColumn(new TextRenderer<>(item -> "%d of %d"
                        .formatted(item.getAvailable(), Optional.ofNullable(item.getQuantity()).orElse(1))))
                .setHeader("Available")
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("7em")
                .setFlexGrow(0);

        var content = getContent();
        content.setSizeUndefined();
        content.setWidthFull();
        content.add(headerBar);
        content.add(itemGrid);
    }

    private static ValueProvider<Item, Object> getGetTitle() {
        return item -> (item.getTitle() == null) || item.getTitle().isBlank() ?
                "%s %s".formatted(item.getManufacturerName(), item.getName()).trim() : item.getTitle();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        itemGrid.setItems(sessionCache.getFilteredViewItems());
    }
}
