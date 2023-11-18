package org.joro.inventory.ui.view.itemdetail;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.joro.inventory.model.*;
import org.joro.inventory.service.*;
import org.joro.inventory.ui.component.GridField;
import org.joro.inventory.ui.component.ImageUploadField;
import org.joro.inventory.ui.component.ImageUploadField.UploadImage;
import org.joro.inventory.ui.component.LabeledPanel;
import org.joro.inventory.ui.util.GridUtil;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;
import org.joro.inventory.ui.view.itemimagegallery.ItemImageGalleryView;
import org.joro.inventory.ui.view.itemlist.ItemListView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.joro.inventory.ui.view.itemdetail.ItemDetailView.ROUTE;

/**
 *
 * <pre>
 * +-content(VerticalLayout)-----------------------------------------------------------------------------------------+
 * | +-headerBar(HorizontalLayout)---------------------------------------------------------------------------------+ |
 * | | +-backButton-+                                                                               +-editButton-+ | |
 * | | |   < Back   |                                                                               |    Edit    | | |
 * | | +------------+                                                                               +------------+ | |
 * | +-------------------------------------------------------------------------------------------------------------+ |
 * | +-editButtonBar(HorizontalLayout)-----------------------------------------------------------------------------+ |
 * | |                                         +-cancelButton-+ +-applyButton-+ +-saveButton-+ +-saveCloseButton-+ | |
 * | |                                         |    Cancel    | |    Apply    | |    Save    | |   Save & Close  | | |
 * | |                                         +--------------+ +-------------+ +------------+ +-----------------+ | |
 * | +-------------------------------------------------------------------------------------------------------------+ |
 * | +-bodyScroller------------------------------------------------------------------------------------------------+ |
 * | | ITEM                                                                                                        | |
 * | | +-itemPanel-----------------------------------------------------------------------------------------------+ | |
 * | | | Name                      Manufacturer              Model                     Type                      | | |
 * | | | +-nameTextField---------+ +-manufacturerTextField-+ +-modelTextField--------+ +-itemCategoryComboBox+-+ | | |
 * | | | |                       | |                       | |                       | |                     |V| | | |
 * | | | +-----------------------+ +-----------------------+ +-----------------------+ +---------------------+-+ | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | | VALUES                                                                                                      | |
 * | | +-valuePanel----------------------------------------------------------------------------------------------+ | |
 * | | | Value                     Asking                    Quantity                  Available                 | | |
 * | | | +-valueIntegerField-----+ +-askingIntegerField----+ +-quantityIntegerField--+ +-availableIntegerField-+ | | |
 * | | | |                       | |                       | |                       | |                       | | | |
 * | | | +-----------------------+ +-----------------------+ +-----------------------+ +-----------------------+ | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | | CONTENT                                                                                                     | |
 * | | +-contentPanel--------------------------------------------------------------------------------------------+ | |
 * | | | Notes                                               +-postingBlock(VerticalLayout)--------------------+ | | |
 * | | | +-notesTextArea-----------------------------------+ | Title                                           | | | |
 * | | | |                                                 | | +-titleTextField------------------------------+ | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | +---------------------------------------------+ | | | |
 * | | | |                                                 | | Description                                     | | | |
 * | | | |                                                 | | +-descriptionTextArea-------------------------+ | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | +---------------------------------------------+ | | | |
 * | | | +-------------------------------------------------+ +-------------------------------------------------+ | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | | LISTS                                                                                                       | |
 * | | +-listPanel-----------------------------------------------------------------------------------------------+ | |
 * | | | Images                                              +-linksBlock(VerticalLayout)----------------------+ | | |
 * | | | +-imageListBox------------------------------------+ | Links                                           | | | |
 * | | | |                                                 | | +-linkListBox---------------------------------+ | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | +---------------------------------------------+ | | | |
 * | | | |                                                 | | Marketplaces                                    | | | |
 * | | | |                                                 | | +-marketplaceListBox--------------------------+ | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | |                                             | | | | |
 * | | | |                                                 | | +---------------------------------------------+ | | | |
 * | | | +-------------------------------------------------+ +-------------------------------------------------+ | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | | SALES                                                                                                       | |
 * | | +-salesPanel----------------------------------------------------------------------------------------------+ | |
 * | | | Prospects                                           Sales                                               | | |
 * | | | +-prospectListBox---------------------------------+ +-saleListBox-------------------------------------+ | | |
 * | | | |                                                 | |                                                 | | | |
 * | | | |                                                 | |                                                 | | | |
 * | | | |                                                 | |                                                 | | | |
 * | | | +-------------------------------------------------+ +-------------------------------------------------+ | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | | +-footerBar(HorizontalLayout)-----------------------------------------------------------------------------+ | |
 * | | |                                          +-removeItemButton--+                                          | | |
 * | | |                                          |    Remove Item    |                                          | | |
 * | | |                                          +-------------------+                                          | | |
 * | | +---------------------------------------------------------------------------------------------------------+ | |
 * | +-------------------------------------------------------------------------------------------------------------+ |
 * +-----------------------------------------------------------------------------------------------------------------+
 * </pre>
 */
@PageTitle("Item Detail")
@Route(value = ROUTE, layout = MainLayout.class)
public class ItemDetailView extends Composite<VerticalLayout>
        implements HasUrlParameter<Long>, AfterNavigationObserver {
    public static final String ROUTE = ItemListView.ROUTE + "/detail";

    public static void navigateTo(Long itemKey) {
        RouteUtil.navigateTo(ItemDetailView.class, itemKey);
    }

    private static final int RESPONSIVE_STEP_SIZE_PX = 400;
    private static final int DEFAULT_TEXT_AREA_HEIGHT_EX = 25;
    private static final int DEFAULT_GRID_LIST_HEIGHT_EX = 25;

    private final transient ItemService itemService;
    private final transient ItemCategoryService itemCategoryService;
    private final transient LinkCategoryService linkCategoryService;
    private final transient MarketplaceService marketplaceService;
    private final transient ProspectService prospectService;

    private final HorizontalLayout headerBar;
    private final HorizontalLayout editButtonBar;
//    private final Select<ItemCategory> itemCategorySelect;
    private final ComboBox<ItemCategory> itemCategorySelect;  // TODO using ComboBox because Select is not binding properly - report this issue
    private final HorizontalLayout footerBar;

//    private final Select<LinkCategory> linkCategorySelect;
    private final ComboBox<LinkCategory> linkCategorySelect;  // TODO using ComboBox because Select is not binding properly - report this issue
    private final GridField<ItemImage> itemImageGridField;
    private final GridField<ItemLink> itemLinkGridField;
//    private final Select<Marketplace> marketplaceSelect;
    private final ComboBox<Marketplace> marketplaceSelect;  // TODO using ComboBox because Select is not binding properly - report this issue
    private final GridField<ItemMarketplace> itemMarketplaceGridField;
//    private final Select<Prospect> prospectSelect;
    private final ComboBox<Prospect> prospectSelect;  // TODO using ComboBox because Select is not binding properly - report this issue
    private final GridField<ItemProspect> itemProspectGridField;
    private final GridField<ItemSale> itemSaleGridField;

    private Long itemKey;
    private transient Item item;
    private final Binder<Item> itemBinder;

    public ItemDetailView(ItemService itemService,
                          ItemCategoryService itemCategoryService,
                          LinkCategoryService linkCategoryService,
                          MarketplaceService marketplaceService,
                          ProspectService prospectService) {

        this.itemService = itemService;
        this.itemCategoryService = itemCategoryService;
        this.linkCategoryService = linkCategoryService;
        this.marketplaceService = marketplaceService;
        this.prospectService = prospectService;

        addClassNames("item-detail-view");

        // back button
        var backButton = new Button("Back", VaadinIcon.ANGLE_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backButton.addClickListener(this::onBackClick);

        // edit button
        var editButton = new Button("Edit", VaadinIcon.PENCIL.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        editButton.addClickListener(this::onEditClick);

        // header bar
        headerBar = new HorizontalLayout();
        headerBar.setWidthFull();
        headerBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerBar.add(backButton);
        headerBar.add(editButton);

        // cancel button
        var cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(this::onCancelClick);

        // apply button
        var applyButton = new Button("Apply");
        applyButton.addClickListener(this::onApplyClick);

        // save button
        var saveButton = new Button("Save");
        saveButton.addClickListener(this::onSaveClick);

        // save & close button
        var saveCloseButton = new Button("Save & Close");
        saveCloseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveCloseButton.addClickListener(this::onSaveCloseClick);

        // edit button bar
        editButtonBar = new HorizontalLayout();
        editButtonBar.setWidthFull();
        editButtonBar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        editButtonBar.add(cancelButton);
        editButtonBar.add(applyButton);
        editButtonBar.add(saveButton);
        editButtonBar.add(saveCloseButton);

        ResponsiveStep[] responsiveSteps = {
                new ResponsiveStep("0", 1),
                new ResponsiveStep("%dpx".formatted(RESPONSIVE_STEP_SIZE_PX), 2),
                new ResponsiveStep("%dpx".formatted(RESPONSIVE_STEP_SIZE_PX * 2), 4)
        };

        // item fields

        var nameTextField = new TextField("Name");
//        nameTextField.setHelperText("The name of the item");

        var manufacturerNameTextField = new TextField("Manufacturer");

        var modelNumberTextField = new TextField("Model");

//        itemCategorySelect = new Select<>();
//        itemCategorySelect.setLabel("Category");
        itemCategorySelect = new ComboBox<>("Category");
        itemCategorySelect.setItemLabelGenerator(ItemCategory::getName);

        var itemForm = new FormLayout();
        itemForm.setResponsiveSteps(responsiveSteps);
        itemForm.add(nameTextField);
        itemForm.add(manufacturerNameTextField);
        itemForm.add(modelNumberTextField);
        itemForm.add(itemCategorySelect);

        var itemPanel = new LabeledPanel("Item", itemForm);
        itemPanel.setWidthFull();

        // value fields

        var valueIntegerField = new IntegerField("Worth");

        var askingIntegerField = new IntegerField("Asking");

        var quantityIntegerField = new IntegerField("Quantity");

        var availableIntegerField = new IntegerField("Available");

        var valueForm = new FormLayout();
        valueForm.setResponsiveSteps(responsiveSteps);
        valueForm.add(valueIntegerField);
        valueForm.add(askingIntegerField);
        valueForm.add(quantityIntegerField);
        valueForm.add(availableIntegerField);

        var valuesPanel = new LabeledPanel("Values", valueForm);
        valuesPanel.setWidthFull();

        // content fields

        var notesTextArea = new TextArea("Notes");
        notesTextArea.setHeight("calc(65px + var(--lumo-space-m) + %dex)".formatted(DEFAULT_TEXT_AREA_HEIGHT_EX));

        var titleTextField = new TextField("Title");
        titleTextField.setWidthFull();

        var descriptionTextArea = new TextArea("Description");
        descriptionTextArea.setWidthFull();
        descriptionTextArea.setHeight(DEFAULT_TEXT_AREA_HEIGHT_EX, Unit.EX);

        var postingBlock = new VerticalLayout();
        postingBlock.setWidthFull();
        postingBlock.setPadding(false);
        postingBlock.add(titleTextField);
        postingBlock.add(descriptionTextArea);

        var contentForm = new FormLayout();
        contentForm.setResponsiveSteps(responsiveSteps);
        contentForm.setWidthFull();
        contentForm.add(notesTextArea);
        contentForm.add(postingBlock);
        contentForm.setColspan(notesTextArea, 1);
        contentForm.setColspan(postingBlock, 3);

        var contentPanel = new LabeledPanel("Content", contentForm);
        contentPanel.setWidthFull();

        // list fields

        var galleryLink = new Button("Image Gallery", LumoIcon.ARROW_RIGHT.create());
        galleryLink.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        galleryLink.addClassNames(LumoUtility.Position.ABSOLUTE);
        galleryLink.getStyle().set("left", "0");
        galleryLink.getStyle().set("right", "0");
        galleryLink.setIconAfterText(true);
        galleryLink.addClickListener(e -> ItemImageGalleryView.navigateTo(itemKey));

        var imageUploadField = new ImageUploadField();
        imageUploadField.setClearButtonVisible(true);

        itemImageGridField = new GridField<>("Images", this::newItemImage);
        itemImageGridField.addClassNames(LumoUtility.AlignSelf.START);
        itemImageGridField.setWidthFull();
        itemImageGridField.setHeight("calc(%dex + var(--lumo-space-m))".formatted(DEFAULT_GRID_LIST_HEIGHT_EX * 2));
        itemImageGridField.addColumn(GridUtil.imageRenderer(ItemImage::getImageData, ItemImage::getContentType),
                        itemImage -> UploadImage.create(itemImage.getContentType(), itemImage.getImageData()),
                        (itemImage, uploadImage) -> {
                            itemImage.setImageData(uploadImage.getData());
                            itemImage.setContentType(uploadImage.getContentType());
                        },
                        imageUploadField)
                .setHeader("Image")
                .setResizable(true)
                .setSortable(true)
                .setFlexGrow(0)
                .setWidth("8em");
        itemImageGridField.addColumn(ItemImage::getCaption, ItemImage::setCaption, new TextField())
                .setHeader("Caption")
                .setResizable(true)
                .setSortable(true);
        // TODO handle sequence number

        var imageBlock = new VerticalLayout();
        imageBlock.addClassNames(LumoUtility.Position.RELATIVE);
        imageBlock.setWidthFull();
        imageBlock.setPadding(false);
        imageBlock.add(galleryLink);
        imageBlock.add(itemImageGridField);

//        linkCategorySelect = new Select<>();
        linkCategorySelect = new ComboBox<>();
        linkCategorySelect.setItemLabelGenerator(LinkCategory::getName);

        itemLinkGridField = new GridField<>("Links", this::newItemLink);
        itemLinkGridField.addClassNames(LumoUtility.AlignSelf.START);
        itemLinkGridField.setWidthFull();
        itemLinkGridField.setHeight(DEFAULT_GRID_LIST_HEIGHT_EX, Unit.EX);
        itemLinkGridField.addColumn(GridUtil.textRenderPropertyOf(ItemLink::getLinkCategory, LinkCategory::getName),
                ItemLink::getLinkCategory, ItemLink::setLinkCategory, linkCategorySelect)
                .setHeader("Type")
                .setResizable(true)
                .setSortable(true);
        itemLinkGridField.addColumn(ItemLink::getCaption, ItemLink::setCaption, new TextField())
                .setHeader("Caption")
                .setResizable(true)
                .setSortable(true);
        itemLinkGridField.addColumn(GridUtil.urlRenderer(ItemLink::getHref),
                        ItemLink::getHref, ItemLink::setHref, new TextField())
                .setHeader("URL")
                .setResizable(true)
                .setSortable(true);
        // TODO handle sequence number

//        marketplaceSelect = new Select<>();
        marketplaceSelect = new ComboBox<>();
        marketplaceSelect.setItemLabelGenerator(Marketplace::getName);

        itemMarketplaceGridField = new GridField<>("Marketplaces", this::newItemMarketplace);
        itemMarketplaceGridField.addClassNames(LumoUtility.AlignSelf.START);
        itemMarketplaceGridField.setWidthFull();
        itemMarketplaceGridField.setHeight(DEFAULT_GRID_LIST_HEIGHT_EX, Unit.EX);
        itemMarketplaceGridField.addColumn(GridUtil.textRenderPropertyOf(ItemMarketplace::getMarketplace, Marketplace::getName),
                        ItemMarketplace::getMarketplace, ItemMarketplace::setMarketplace, marketplaceSelect)
                .setHeader("Marketplace")
                .setResizable(true)
                .setSortable(true);
        itemMarketplaceGridField.addColumn(GridUtil.urlRenderer(ItemMarketplace::getPostingUrl),
                ItemMarketplace::getPostingUrl, ItemMarketplace::setPostingUrl, new TextField())
                .setHeader("Posting URL")
                .setResizable(true)
                .setSortable(true);

        var linksBlock = new VerticalLayout();
        linksBlock.setWidthFull();
        linksBlock.setPadding(false);
        linksBlock.add(itemLinkGridField);
        linksBlock.add(itemMarketplaceGridField);

        var listsForm = new FormLayout();
        listsForm.setResponsiveSteps(responsiveSteps);
        listsForm.setWidthFull();
        listsForm.add(imageBlock);
        listsForm.add(linksBlock);
        listsForm.setColspan(imageBlock, 2);
        listsForm.setColspan(linksBlock, 2);

        var listsPanel = new LabeledPanel("Lists", listsForm);
        listsPanel.setWidthFull();

        // sales fields

//        prospectSelect = new Select<>();
        prospectSelect = new ComboBox<>();
        prospectSelect.setItemLabelGenerator(Prospect::getName);

        itemProspectGridField = new GridField<>("Prospects", this::newItemProspect);
        itemProspectGridField.addClassNames(LumoUtility.AlignSelf.START);
        itemProspectGridField.setHeight(DEFAULT_GRID_LIST_HEIGHT_EX, Unit.EX);
        itemProspectGridField.addColumn(GridUtil.textRenderPropertyOf(ItemProspect::getProspect, Prospect::getName),
                        ItemProspect::getProspect, ItemProspect::setProspect, prospectSelect)
                .setHeader("Prospect")
                .setResizable(true)
                .setSortable(true);
        itemProspectGridField.addColumn(new IconRenderer<>(itemProspect -> {
                            var icon = itemProspect.isReserved() ? VaadinIcon.DOT_CIRCLE.create() : VaadinIcon.CIRCLE_THIN.create();
                            icon.setSize("var(--lumo-font-size-s)");
                            icon.setColor("var(--lumo-contrast-70pct)");
                            return icon;
                        }, itemProspect -> ""),
                ItemProspect::isReserved, ItemProspect::setReserved, new Checkbox())
                .setHeader("Reserved")
                .setResizable(true)
                .setSortable(true);

        itemSaleGridField = new GridField<>("Sold", this::newItemSale);
        itemSaleGridField.addClassNames(LumoUtility.AlignSelf.START);
        itemSaleGridField.setHeight(DEFAULT_GRID_LIST_HEIGHT_EX, Unit.EX);

        final var dateFormat = "yyyy-MM-dd";
        var datePickerEditorComponent = new DatePicker();
        var i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat(dateFormat);
        datePickerEditorComponent.setI18n(i18n);
        datePickerEditorComponent.getI18n().setDateFormat(dateFormat);
        itemSaleGridField.addColumn(new LocalDateRenderer<>(ItemSale::getSaleDate, dateFormat),
                        ItemSale::getSaleDate, ItemSale::setSaleDate, datePickerEditorComponent)
                .setHeader("Date")
                .setResizable(true)
                .setSortable(true);

        itemSaleGridField.addColumn(new NumberRenderer<>(ItemSale::getSalePrice, NumberFormat.getCurrencyInstance()),
                        ItemSale::getSalePrice, ItemSale::setSalePrice, new IntegerField())
                .setHeader("Price")
                .setResizable(true)
                .setSortable(true);

        itemSaleGridField.addColumn(ItemSale::getBuyerName, ItemSale::setBuyerName, new TextField())
                .setHeader("Buyer")
                .setResizable(true)
                .setSortable(true);

        var salesForm = new FormLayout();
        salesForm.setResponsiveSteps(responsiveSteps);
        salesForm.add(itemProspectGridField);
        salesForm.add(itemSaleGridField);
        salesForm.setColspan(itemProspectGridField, 2);
        salesForm.setColspan(itemSaleGridField, 2);

        var salesPanel = new LabeledPanel("Sales", salesForm);
        salesPanel.setWidthFull();

        // remove item button
        var removeItemButton = new Button("Remove Item", VaadinIcon.TRASH.create());
        removeItemButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        removeItemButton.addClickListener(this::onRemoveItemClick);

        // footer bar
        footerBar = new HorizontalLayout();
        footerBar.setWidthFull();
        footerBar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerBar.add(removeItemButton);

        // body
        var body = new VerticalLayout();
        body.setPadding(false);
        body.add(itemPanel);
        body.add(valuesPanel);
        body.add(contentPanel);
        body.add(listsPanel);
        body.add(salesPanel);
        body.add(footerBar);

        // body scroller
        var bodyScroller = new Scroller(Scroller.ScrollDirection.VERTICAL);
        bodyScroller.setSizeFull();
        bodyScroller.setContent(body);

        // content
        var content = getContent();
        content.add(headerBar);
        content.add(editButtonBar);
        content.add(bodyScroller);

        // binder

        itemBinder = new Binder<>();

        itemBinder.forField(nameTextField)
                .asRequired("Item name is required")
                .bind(Item::getName, Item::setName);
        itemBinder.forField(manufacturerNameTextField)
                .bind(Item::getManufacturerName, Item::setManufacturerName);
        itemBinder.forField(modelNumberTextField)
                .bind(Item::getModelNumber, Item::setModelNumber);
        itemBinder.forField(itemCategorySelect)
                .bind(Item::getItemCategory, Item::setItemCategory);

        itemBinder.forField(valueIntegerField)
                .bind(Item::getValue, Item::setValue);
        itemBinder.forField(askingIntegerField)
                .bind(Item::getAskingPrice, Item::setAskingPrice);
        itemBinder.forField(quantityIntegerField)
                .bind(Item::getQuantity, Item::setQuantity);
        itemBinder.bindReadOnly(availableIntegerField,
                i -> Optional.ofNullable(i.getQuantity()).orElse(1)
                        - Optional.ofNullable(i.getItemSales()).map(List::size).orElse(0));

        itemBinder.forField(notesTextArea)
                .bind(Item::getNotes, Item::setNotes);
        itemBinder.forField(itemLinkGridField)
                .bind(Item::getItemLinks, Item::setItemLinks);
        itemBinder.forField(itemImageGridField)
                .bind(Item::getItemImages, Item::setItemImages);

        itemBinder.forField(titleTextField)
                .bind(Item::getTitle, Item::setTitle);
        itemBinder.forField(descriptionTextArea)
                .bind(Item::getDescription, Item::setDescription);
        itemBinder.forField(itemMarketplaceGridField)
                .bind(Item::getItemMarketplaces, Item::setItemMarketplaces);

        itemBinder.forField(itemProspectGridField)
                .bind(Item::getItemProspects, Item::setItemProspects);
        itemBinder.forField(itemSaleGridField)
                .bind(Item::getItemSales, Item::setItemSales);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long itemKey) {
        this.itemKey = itemKey;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void afterNavigation(AfterNavigationEvent event) {
        item = Optional.ofNullable(itemKey).map(itemService::fetch).orElse(new Item());
        if (item == null) {
            Notification.show("The requested item was not found, key = %s".formatted(itemKey),
                            3000,
                            Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            ItemListView.navigateTo();
            return;
        }

        itemCategorySelect.setItems(itemCategoryService.fetchAll());
        linkCategorySelect.setItems(linkCategoryService.fetchAll());
        marketplaceSelect.setItems(marketplaceService.fetchAll());
        prospectSelect.setItems(prospectService.fetchAll());

        itemBinder.readBean(item);
        setEditMode(itemKey == null);
    }

    private void setEditMode(boolean editMode) {
        itemBinder.setReadOnly(!editMode);
        headerBar.setVisible(!editMode);
        editButtonBar.setVisible(editMode);
        footerBar.setVisible(!editMode);
    }

    private void onBackClick(ClickEvent<Button> event) {
        ItemListView.navigateTo();
    }

    private void onEditClick(ClickEvent<Button> event) {
        setEditMode(true);
    }

    private void onRemoveItemClick(ClickEvent<Button> event) {
        var removeItemDialog = new ConfirmDialog("Remove Item",
                "Are you sure you want to remove this item (%s)?".formatted(item.getName()),
                "Remove", e -> onRemoveItem());
        removeItemDialog.setConfirmButtonTheme("primary error");
        removeItemDialog.setCancelText("Keep");
        removeItemDialog.setCancelable(true);
        removeItemDialog.open();
    }

    private void onRemoveItem() {
        itemService.remove(item);
        ItemListView.navigateTo();
    }

    private void onCancelClick(ClickEvent<Button> event) {
        if (itemKey == null) {
            ItemListView.navigateTo();
            return;
        }

        navigateTo(itemKey);
    }

    private void onApplyClick(ClickEvent<Button> event) {
        save();
    }

    private void onSaveClick(ClickEvent<Button> event) {
        if (save()) {
            navigateTo(itemKey);
        }
    }

    private void onSaveCloseClick(ClickEvent<Button> event) {
        if (save()) {
            ItemListView.navigateTo();
        }
    }

    private <T extends HasItem> T newGridItem(Supplier<T> gridItemFactory) {
        var gridItem = gridItemFactory.get();
        gridItem.setItem(item);

        return gridItem;
    }

    private ItemLink newItemLink() {
        return newGridItem(ItemLink::new);
    }

    private ItemImage newItemImage() {
        return newGridItem(ItemImage::new);
    }

    private ItemMarketplace newItemMarketplace() {
        return newGridItem(ItemMarketplace::new);
    }

    private ItemProspect newItemProspect() {
        return newGridItem(ItemProspect::new);
    }

    private ItemSale newItemSale() {
        return newGridItem(ItemSale::new);
    }

    private boolean save() {
        if (itemImageGridField.isEditing() ||
                itemLinkGridField.isEditing() ||
                itemMarketplaceGridField.isEditing() ||
                itemProspectGridField.isEditing() ||
                itemSaleGridField.isEditing()) {
            Notification.show("""
                There are list edits in progress.
                Please finish those edits before saving the item.""",
                    3000, Notification.Position.MIDDLE);
            return false;
        }

        if (!itemBinder.validate().isOk()) {
            return false;
        }

        itemBinder.writeBeanIfValid(item);
        item = itemService.save(item);
        itemKey = item.getItemKey();
        return true;
    }
}
