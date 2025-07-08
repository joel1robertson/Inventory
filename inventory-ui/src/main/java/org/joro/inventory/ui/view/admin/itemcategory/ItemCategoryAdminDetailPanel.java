package org.joro.inventory.ui.view.admin.itemcategory;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joro.inventory.ui.model.data.ItemCategory;
import org.joro.inventory.ui.component.adminpanel.AdminDetailPanel;
import org.joro.inventory.ui.component.editpanel.BinderFieldContentPanel;

public class ItemCategoryAdminDetailPanel extends AdminDetailPanel<ItemCategory, VerticalLayout> {

    @Override
    protected String getTitle() {
        return "Item Category";
    }

    @Override
    protected BinderFieldContentPanel<ItemCategory, VerticalLayout> createEditContentPanel() {
        return new ItemCategoryDetailContentPanel();
    }

    private static class ItemCategoryDetailContentPanel extends BinderFieldContentPanel<ItemCategory, VerticalLayout> {
        public ItemCategoryDetailContentPanel() {
            var nameTextField = new TextField("Name");

            var content = getContent();
            content.add(nameTextField);

            forField(nameTextField)
                    .asRequired()
                    .withNullRepresentation("")
                    .bind(ItemCategory::getName, ItemCategory::setName);
        }
    }
}
