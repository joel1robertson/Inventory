package org.joro.inventory.ui.view.admin.marketplace;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joro.inventory.model.Marketplace;
import org.joro.inventory.ui.component.adminpanel.AdminDetailPanel;
import org.joro.inventory.ui.component.editpanel.BinderFieldContentPanel;

public class MarketplaceAdminDetailPanel extends AdminDetailPanel<Marketplace, VerticalLayout> {

    @Override
    protected String getTitle() {
        return "Marketplace";
    }

    @Override
    protected BinderFieldContentPanel<Marketplace, VerticalLayout> createEditContentPanel() {
        return new MarketplaceDetailContentPanel();
    }

    private static class MarketplaceDetailContentPanel extends BinderFieldContentPanel<Marketplace, VerticalLayout> {
        public MarketplaceDetailContentPanel() {
            var nameTextField = new TextField("Name");
            var siteUrlTextField = new TextField("Site URL");

            var content = getContent();
            content.add(nameTextField);
            content.add(siteUrlTextField);

            forField(nameTextField)
                    .asRequired()
                    .withNullRepresentation("")
                    .bind(Marketplace::getName, Marketplace::setName);

            forField(siteUrlTextField)
                    .withNullRepresentation("")
                    .bind(Marketplace::getSiteUrl, Marketplace::setSiteUrl);
        }
    }
}
