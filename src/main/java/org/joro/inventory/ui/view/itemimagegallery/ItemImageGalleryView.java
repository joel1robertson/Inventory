package org.joro.inventory.ui.view.itemimagegallery;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.joro.inventory.service.ItemService;
import org.joro.inventory.ui.util.RouteUtil;
import org.joro.inventory.ui.view.MainLayout;
import org.joro.inventory.ui.view.itemdetail.ItemDetailView;
import org.joro.inventory.ui.view.itemlist.ItemListView;

import java.util.Optional;

import static org.joro.inventory.ui.view.itemimagegallery.ItemImageGalleryView.ROUTE;

@PageTitle("Item Image Gallery")
@Route(value = ROUTE, layout = MainLayout.class)
public class ItemImageGalleryView extends Main
        implements HasUrlParameter<Long>, AfterNavigationObserver, HasComponents, HasStyle {
    public static final String ROUTE = ItemDetailView.ROUTE + "/gallery";
    private final H2 header;

    public static void navigateTo(Long itemKey) {
        RouteUtil.navigateTo(ItemImageGalleryView.class, itemKey);
    }

    private final transient ItemService itemService;
    private final OrderedList imageContainer;

    private Long itemKey;

    public ItemImageGalleryView(ItemService itemService) {
        this.itemService = itemService;

        header = new H2();
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);

        var description = new Paragraph();
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);

        var headerContainer = new VerticalLayout();
        headerContainer.add(header);
        headerContainer.add(description);

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        container.add(headerContainer);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(
                Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        addClassNames("image-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        add(container);
        add(imageContainer);
    }

    @Override
    public void setParameter(BeforeEvent event, Long itemKey) {
        this.itemKey = itemKey;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void afterNavigation(AfterNavigationEvent event) {
        var item = itemService.fetchGalleryFor(itemKey);
        if (item == null) {
            Notification.show("The requested item was not found, so no images to show, key = %s".formatted(itemKey),
                            3000,
                            Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            ItemListView.navigateTo();
            return;
        }

        header.setText(Optional.ofNullable(item.getTitle()).orElse(item.getName()));

        item.getItemImages().stream()
                .map(ImageCard::new)
                .forEach(imageContainer::add);
    }
}
