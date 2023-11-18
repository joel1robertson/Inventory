package org.joro.inventory.ui.component.editpanel;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

/**
 * A modal component used to display content either as read-only or as editable.
 *
 * <pre>
 * +-content(VerticalLayout)----------------------------------------------------------+
 * | +-headerBlock(HorizontalLayout)------------------------------------------------+ |
 * | | +-titleBlock(Div)--+     +-headerButtonBar(HorizontalLayout)---------------+ | |
 * | | | +-titleSpan-++-+ |     | +-editButton-+ +-discardButton-+ +-saveButton-+ | | |
 * | | | | Title     ||*| |     | |    Edit    | |    Discard    | |    Save    | | | |
 * | | | +-----------++-+ |     | +------------+ +---------------+ +------------+ | | |
 * | | +------------------+     +-------------------------------------------------+ | |
 * | +------------------------------------------------------------------------------+ |
 * | +-editContentPanel-------------------------------------------------------------+ |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | |                                                                              | |
 * | +------------------------------------------------------------------------------+ |
 * +----------------------------------------------------------------------------------+
 * </pre>
 */
public abstract class EditDiscardSavePanel<T, C extends EditFieldContentPanel<T, ?>> extends EditDonePanel<C> {

    private final Span modifiedSpan;
    private final Button saveButton;
    private final C editContentPanel;

    private boolean modified;

    protected EditDiscardSavePanel() {
        modifiedSpan = new Span("*");

        addTitleComponent(modifiedSpan);

        setDoneButtonText("Discard");

        saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(this::onSave);

        addHeaderButton(saveButton);

        editContentPanel = getEditContentPanel();

        setModified(false);
    }

    @Override
    public void setEditing(boolean editing) {
        super.setEditing(editing);

        saveButton.setVisible(editing);
        editContentPanel.setEditing(editing);
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
        modifiedSpan.setVisible(modified && isEditing());
    }

    protected void onModified() {
        setModified(true);
    }

    @Override
    protected void onDone(ClickEvent<Button> event) {
        editContentPanel.revert();
        super.onDone(event);

        setModified(false);
    }

    protected void onSave(ClickEvent<Button> event) {
        if (!editContentPanel.save()) {
            Notification.show("%s not saved".formatted(getTitle()), 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        setEditing(false);
        setModified(false);
    }

    protected abstract C createEditContentPanel();

    public T getItem() {
        return editContentPanel.getItem();
    }

    public void setItem(T item) {
        editContentPanel.setItem(item);
    }

}
