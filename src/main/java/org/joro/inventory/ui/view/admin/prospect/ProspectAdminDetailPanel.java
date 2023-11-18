package org.joro.inventory.ui.view.admin.prospect;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.joro.inventory.model.Prospect;
import org.joro.inventory.ui.component.adminpanel.AdminDetailPanel;
import org.joro.inventory.ui.component.editpanel.BinderFieldContentPanel;

public class ProspectAdminDetailPanel extends AdminDetailPanel<Prospect, VerticalLayout> {

    @Override
    protected String getTitle() {
        return "Prospect";
    }

    @Override
    protected BinderFieldContentPanel<Prospect, VerticalLayout> createEditContentPanel() {
        return new ProspectDetailContentPanel();
    }

    private static class ProspectDetailContentPanel extends BinderFieldContentPanel<Prospect, VerticalLayout> {
        public ProspectDetailContentPanel() {
            var nameTextField = new TextField("Name");

            var content = getContent();
            content.add(nameTextField);

            forField(nameTextField)
                    .asRequired()
                    .withNullRepresentation("")
                    .bind(Prospect::getName, Prospect::setName);
        }
    }
}
