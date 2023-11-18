package org.joro.inventory.ui.view.admin.linkcategory;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joro.inventory.model.LinkCategory;
import org.joro.inventory.ui.component.adminpanel.AdminDetailPanel;
import org.joro.inventory.ui.component.editpanel.BinderFieldContentPanel;

public class LinkCategoryAdminDetailPanel extends AdminDetailPanel<LinkCategory, VerticalLayout> {

    @Override
    protected String getTitle() {
        return "Link Category";
    }

    @Override
    protected BinderFieldContentPanel<LinkCategory, VerticalLayout> createEditContentPanel() {
        return new LinkCategoryDetailContentPanel();
    }

    private static class LinkCategoryDetailContentPanel extends BinderFieldContentPanel<LinkCategory, VerticalLayout> {
        public LinkCategoryDetailContentPanel() {
            var nameTextField = new TextField("Name");

            var content = getContent();
            content.add(nameTextField);

            forField(nameTextField)
                    .asRequired()
                    .withNullRepresentation("")
                    .bind(LinkCategory::getName, LinkCategory::setName);
        }
    }
}
