package org.joro.inventory.ui.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.joro.inventory.ui.view.admin.itemcategory.ItemCategoryAdminView;
import org.joro.inventory.ui.view.admin.linkcategory.LinkCategoryAdminView;
import org.joro.inventory.ui.view.admin.marketplace.MarketplaceAdminView;
import org.joro.inventory.ui.view.admin.prospect.ProspectAdminView;
import org.joro.inventory.ui.view.itemlist.ItemListView;

import java.util.Arrays;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    public MainLayout() {
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        var appName = new H1("Inventory");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);

        var layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        layout.add(appName);

        // wrap the links in a list to improve accessibility
        var navLinkList = new UnorderedList();
        navLinkList.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        Arrays.stream(createNavMenuItems()).forEach(navLinkList::add);

        var nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);
        nav.add(navLinkList);

        var header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);
        header.add(layout, nav);
        return header;
    }

    private NavItemInfo[] createNavMenuItems() {
        return new NavItemInfo[]{ //
                new NavItemInfo("Items", "la la-columns", ItemListView.class),
                new NavItemInfo("Item Categories", "la la-columns", ItemCategoryAdminView.class),
                new NavItemInfo("Marketplaces", "la la-columns", MarketplaceAdminView.class),
                new NavItemInfo("Link Categories", "la la-columns", LinkCategoryAdminView.class),
                new NavItemInfo("Prospects", "la la-columns", ProspectAdminView.class)
//                new NavItemInfo("Image List", "la la-th-list", ImageListView.class), //
        };
    }

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class NavItemInfo extends ListItem {
        private final Class<? extends Component> viewClass;

        public NavItemInfo(String menuTitle, String iconClass, Class<? extends Component> viewClass) {
            this.viewClass = viewClass;

            var text = new Span(menuTitle);
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            var link = new RouterLink();
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(viewClass);
            link.add(new LineAwesomeIcon(iconClass), text);

            add(link);
        }

        public Class<?> getViewClass() {
            return viewClass;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * <a href="https://icons8.com/line-awesome">LineAwesome</a>
         */
//        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineAwesomeClassNames) {
                // Use Lumo class names for suitable font styling
                addClassNames(FontSize.LARGE, TextColor.SECONDARY);
                if (!lineAwesomeClassNames.isEmpty()) {
                    addClassNames(lineAwesomeClassNames);
                }
            }
        }

    }

}
