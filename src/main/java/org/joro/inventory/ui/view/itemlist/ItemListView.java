package org.joro.inventory.ui.view.itemlist;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.event.SortEvent;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoIcon;
import org.joro.inventory.cache.SessionCache;
import org.joro.inventory.cache.UICache;
import org.joro.inventory.model.*;
import org.joro.inventory.service.ItemService;
import org.joro.inventory.ui.util.GridUtil;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;
import org.joro.inventory.ui.view.itemdetail.ItemDetailView;
import org.joro.inventory.ui.view.itemreport.ItemReportView;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * <pre>
 * +-content(VerticalLayout)---------------------------------------------------------------+
 * | +-headerBar(HorizontalLayout)-------------------------------------------------------+ |
 * | |                            +-buttonBar(HorizontalLayout)--------------------------+ |
 * | | +-filterTextField-+        | +-refreshButton-+ +-reportButton-+ +-addItemButton-+ | |
 * | | | Search for...   |        | |    Refresh    | |    Report    | |   Add Item    | | |
 * | | +-----------------+        | +---------------+ +--------------+ +---------------+ | |
 * | |                            +------------------------------------------------------+ |
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
@Route(value = ItemListView.ROUTE, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ItemListView extends Composite<VerticalLayout> implements AfterNavigationObserver {
    public static final String ROUTE = "item";
    public static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();

    public static void navigateTo() {
        RouteUtil.navigateTo(ItemListView.class);
    }

    private final transient ItemService itemService;
    private final transient SessionCache sessionCache;
    private final transient UICache uiCache;

    private final TextField filterTextField;
    private final Grid<Item> itemGrid;
    private final Span footerItemCount;
    private final Span footerValueTotal;
    private final Span footerAskingTotal;
    private final Span footerAvailableQuantityTotal;

    private transient List<Item> items;

    public ItemListView(ItemService itemService, SessionCache sessionCache, UICache uiCache) {
        this.itemService = itemService;
        this.sessionCache = sessionCache;
        this.uiCache = uiCache;

        addClassNames("item-list-view");

        // filter text field
        filterTextField = new TextField();
        filterTextField.setPrefixComponent(LumoIcon.SEARCH.create());
        filterTextField.setPlaceholder("Search for...");
        filterTextField.setClearButtonVisible(true);
        filterTextField.setWidth("24em");
        filterTextField.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextField.addValueChangeListener(this::onFilterTextChange);

        // refresh button
        var refreshButton = new Button("Refresh", VaadinIcon.REFRESH.create());
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(this::onRefreshClick);

        // report button
        var reportButton = new Button("Report", VaadinIcon.TABLE.create());
        reportButton.addClickListener(this::onReportClick);

        // add item button
        var addItemButton = new Button("Add Item", VaadinIcon.PLUS.create());
        addItemButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addItemButton.addClickListener(this::onAddItemClick);

        // button bar
        var buttonBar = new HorizontalLayout();
        buttonBar.add(refreshButton);
        buttonBar.add(reportButton);
        buttonBar.add(addItemButton);

        // header bar
        var headerBar = new HorizontalLayout();
        headerBar.setWidthFull();
        headerBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerBar.add(filterTextField);
        headerBar.add(buttonBar);

        // item grid header components
        var imageHeader = VaadinIcon.PICTURE.create();
        imageHeader.setSize("1.125em");

        // item grid footer components
        footerItemCount = new Span();
        footerValueTotal = new Span();
        footerAskingTotal = new Span();
        footerAvailableQuantityTotal = new Span();

        // item grid
        itemGrid = new Grid<>();
        itemGrid.setSizeFull();
        itemGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        itemGrid.setMultiSort(true);
//        itemGrid.setColumnReorderingAllowed(true);
        itemGrid.addSortListener(this::onItemGridSort);
        itemGrid.addItemClickListener(this::onItemGridRowClick);

        itemGrid.addColumn(GridUtil.imageRenderer(GridUtil.propertyOf(GridUtil.indexOf(Item::getItemImages, 0), ItemImage::getImageData),
                        GridUtil.propertyOf(GridUtil.indexOf(Item::getItemImages, 0), ItemImage::getContentType)))
                .setHeader(imageHeader)
                .setComparator(Comparator.comparingInt(o -> o.getItemImages().size()))
                .setWidth("3.5em")
                .setFlexGrow(0);
        itemGrid.addColumn(Item::getName)
                .setHeader("Item")
                .setFlexGrow(6)
                .setFooter(new Span(new Text("Totals: "), footerItemCount));
        itemGrid.addColumn(Item::getManufacturerName)
                .setHeader("Make")
                .setFlexGrow(1);
        itemGrid.addColumn(Item::getModelNumber)
                .setHeader("Model")
                .setFlexGrow(1);
        itemGrid.addColumn(GridUtil.propertyOf(Item::getItemCategory, ItemCategory::getName))
                .setHeader("Type")
                .setFlexGrow(1);
        itemGrid.addColumn(new NumberRenderer<>(Item::getValue, CURRENCY_FORMATTER))
                .setHeader("Worth")
                .setComparator(Comparator.comparing(Item::getValue, Comparator.nullsFirst(Comparator.naturalOrder())))
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("9em")
                .setFlexGrow(0)
                .setFooter(footerValueTotal);
        itemGrid.addColumn(new NumberRenderer<>(Item::getAskingPrice, CURRENCY_FORMATTER))
                .setHeader("Asking")
                .setComparator(Comparator.comparing(Item::getAskingPrice, Comparator.nullsFirst(Comparator.naturalOrder())))
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("9em")
                .setFlexGrow(0)
                .setFooter(footerAskingTotal);
        itemGrid.addColumn(new TextRenderer<>(item -> "%d of %d"
                        .formatted(item.getAvailable(), Optional.ofNullable(item.getQuantity()).orElse(1))))
                .setHeader("Available")
                .setComparator(Item::getAvailable)
                .setTextAlign(ColumnTextAlign.END)
                .setWidth("7em")
                .setFlexGrow(0)
                .setFooter(footerAvailableQuantityTotal);

        itemGrid.getColumns()
                .forEach(column -> {
                    column.setResizable(true);
                    column.setSortable(true);
                });

        var content = getContent();
        content.setSizeFull();
        content.add(headerBar);
        content.add(itemGrid);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populateItemGrid();
        filterTextField.setValue(uiCache.getFilterText());
        itemGrid.sort(uiCache.getSortOrders());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        filterTextField.focus();
    }

    private void onFilterTextChange(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        if (event.isFromClient()) {
            uiCache.setFilterText(filterTextField.getValue());
        }
        filterItemGrid();
    }

    private void onRefreshClick(ClickEvent<Button> event) {
        populateItemGrid();
    }

    private void onReportClick(ClickEvent<Button> buttonClickEvent) {
        ItemReportView.navigateTo();
    }

    private void onAddItemClick(ClickEvent<Button> event) {
        ItemDetailView.navigateTo(null);
    }

    private void onItemGridSort(SortEvent<Grid<Item>, GridSortOrder<Item>> event) {
        if (event.isFromClient()) {
            sessionCache.setFilteredViewItems(event.getSource().getListDataView().getItems().toList());
            uiCache.setSortOrders(event.getSortOrder());
        }
    }

    private void onItemGridRowClick(ItemClickEvent<Item> event) {
        ItemDetailView.navigateTo(event.getItem().getItemKey());
    }

    private void populateItemGrid() {
        items = itemService.fetchAll();
        filterItemGrid();
    }

    private void filterItemGrid() {
        List<Item> filteredItems = items.stream()
                .filter(item -> item.getAvailable() > 0)
                .filter(containsSearchText(Item::getName)
                        .or(containsSearchText(Item::getDescription))
                        .or(containsSearchText(Item::getManufacturerName))
                        .or(containsSearchText(Item::getModelNumber))
                        .or(containsSearchText(Item::getTitle))
                        .or(containsSearchText(Item::getNotes))
                        .or(containsSearchText(Item::getItemCategory, ItemCategory::getName))
                        .or(anyContainSearchText(Item::getItemLinks, ItemLink::getLinkCategory, LinkCategory::getName))
                        .or(anyContainSearchText(Item::getItemProspects, ItemProspect::getProspect, Prospect::getName))
                        .or(anyContainSearchText(Item::getItemMarketplaces, ItemMarketplace::getMarketplace, Marketplace::getName))
                )
                .toList();

        var itemCount = filteredItems.size();
        var totalValue = filteredItems.stream()
                .map(Item::getValue)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        var totalAsking = filteredItems.stream()
                .map(Item::getAskingPrice)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        var totalAvailable = filteredItems.stream()
                .map(Item::getAvailable)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        var totalQuantity = filteredItems.stream()
                .map(Item::getQuantity)
                .map(Optional::ofNullable)
                .map(o -> o.orElse(1))
                .mapToInt(Integer::intValue)
                .sum();

        itemGrid.setItems(filteredItems);

        footerItemCount.setText(Integer.toString(itemCount));
        footerValueTotal.setText(CURRENCY_FORMATTER.format(totalValue));
        footerAskingTotal.setText(CURRENCY_FORMATTER.format(totalAsking));
        footerAvailableQuantityTotal.setText("%d of %d".formatted(totalAvailable, totalQuantity));

        sessionCache.setFilteredViewItems(itemGrid.getListDataView().getItems().toList());
    }

    private boolean containsSearchText(String fieldValue) {
        return (fieldValue != null) &&
                fieldValue.toUpperCase().contains(filterTextField.getValue().toUpperCase());
    }

    private Predicate<Item> containsSearchText(Function<Item, String> itemPropertyValue) {
        return item -> containsSearchText(itemPropertyValue.apply(item));
    }

    private <P> Predicate<Item> containsSearchText(Function<Item, P> itemPropertyValue, Function<P, String> propertyValue) {
        return item -> Optional.ofNullable(itemPropertyValue.apply(item))
                .map(propertyValue)
                .filter(this::containsSearchText)
                .isPresent();
    }

    private <A, P> Predicate<Item> anyContainSearchText(Function<Item, Collection<A>> itemPropertyValues,
                                                        Function<A, P> associationValue, Function<P, String> propertyValue) {
        return item -> itemPropertyValues.apply(item).stream()
                .map(associationValue)
                .filter(Objects::nonNull)
                .map(propertyValue)
                .anyMatch(this::containsSearchText);
    }
}
