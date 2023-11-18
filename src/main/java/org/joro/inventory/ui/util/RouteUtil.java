package org.joro.inventory.ui.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.*;

/**
 * Convenience methods for routing.
 */
public final class RouteUtil extends LibraryClass {

    private static final String NULL_PARAMETER = "-";


    private RouteUtil() {
        // utility class - do not instantiate
    }

    /**
     * Convenience method for navigating to an external URL.
     * <p>
     * Not to be confused with opening a new tab/window.
     * That is done via a browser link (or javascript) with the target set to BLANK.
     *
     * @param externalUrl the URL to an external page (segments must already be URL-encoded)
     */
    public static void navigateToExternalUrl(String externalUrl) {
        UI.getCurrent().getPage().setLocation(externalUrl);
    }

    /**
     * Convenience method for opening a view in a new tab.
     *
     * @param viewClass the view to navigate to
     */
    public static void open(Class <? extends Component> viewClass) {
        String route = viewClass.getAnnotation(Route.class).value();
        UI.getCurrent().getPage().open(route);
    }

    /**
     * Convenience method for navigating to a view with no parameters.
     *
     * @param viewClass the view to navigate to
     */
    public static void navigateTo(Class <? extends Component> viewClass) {
        UI.getCurrent().navigate(viewClass);
    }

    /**
     * Convenience method for navigating to a view with {@link HasUrlParameter}.
     *
     * @param viewClass the view to navigate to
     * @param urlParameter the URL parameter of the view
     */
    public static <C extends Component & HasUrlParameter<T>, T> void navigateTo(Class<C> viewClass,
                                                                                T urlParameter) {
        UI.getCurrent().navigate(viewClass, urlParameter);
    }

    /**
     * Convenience method for navigating to a view with {@link RouteParameters}.
     *
     * @param viewClass the view to navigate to
     * @param routeParameters the route parameters for the view
     */
    public static void navigateTo(Class<? extends Component> viewClass,
                                  RouteParameters routeParameters) {
        UI.getCurrent().navigate(viewClass, routeParameters);
    }

    public static <C extends Component & HasUrlParameter<T>, T> void deepLinkTo(Class<C> viewClass, T parameterValue) {
        // build deepLinkingUrl using parameterValue of viewClass
        var deepLinkingUrl = RouteConfiguration.forSessionScope().getUrl(viewClass, parameterValue);
        // assign the deep linking URL directly using page's History object
        // this changes the URL in the browser, but doesn't reload the page
        UI.getCurrent().getPage().getHistory().replaceState(null, deepLinkingUrl);
    }
}
