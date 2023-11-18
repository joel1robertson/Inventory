package org.joro.inventory.ui.component.editpanel;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A modal component used to display content either as read-only or as editable.
 *
 * <pre>
 * +-content(VerticalLayout)----------------------------------------------------------+
 * | +-headerBlock(HorizontalLayout)------------------------------------------------+ |
 * | | +-titleBlock(Div)-+                    +-headerButtonBar(HorizontalLayout)-+ | |
 * | | | +-titleSpan---+ |                    | +-editButton---+ +-doneButton---+ | | |
 * | | | | Title       | |                    | |     Edit     | |     Done     | | | |
 * | | | +-------------+ |                    | +--------------+ +--------------+ | | |
 * | | +-----------------+                    +-----------------------------------+ | |
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
public abstract class EditDonePanel<C extends EditContentPanel<?>> extends Composite<VerticalLayout>
        implements HasStyle, HasSize, HasEnabled {

    private final Div titleBlock;
    private final HorizontalLayout headerButtonBar;
    private final Button editButton;
    private final Button doneButton;
    private final C editContentPanel;

    private boolean enabled;
    private boolean editing;

    protected EditDonePanel() {
        var titleSpan = new Span(getTitle());

        titleBlock = new Div();
        titleBlock.add(titleSpan);

        editButton = new Button("Edit", VaadinIcon.EDIT.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        editButton.addClickListener(this::onEdit);

        doneButton = new Button("Done");
        doneButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
        doneButton.addClickListener(this::onDone);

        headerButtonBar = new HorizontalLayout();
        headerButtonBar.add(editButton);
        headerButtonBar.add(doneButton);

        var headerBlock = new HorizontalLayout();
        headerBlock.setWidthFull();
        headerBlock.setAlignItems(FlexComponent.Alignment.BASELINE);
        headerBlock.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerBlock.add(titleBlock);
        headerBlock.add(headerButtonBar);

        editContentPanel = createEditContentPanel();
        editContentPanel.setSizeFull();

        var content = getContent();
        content.addClassName("editable-panel");
        content.add(headerBlock);
        content.add(editContentPanel);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        setEditing(false);
    }

    protected void setDoneButtonText(String text) {
        doneButton.setText(text);
    }

    protected void addTitleComponent(Component component) {
        titleBlock.add(component);
    }

    protected void addHeaderButton(Button button) {
        if (button.hasThemeName("primary")) {
            doneButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }
        headerButtonBar.add(button);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (!enabled) {
            setEditing(false);
        }
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;

        if (editing) {
            setEnabled(true);
        }

        editButton.setVisible(!editing);
        doneButton.setVisible(editing);
    }

    protected void onEdit(ClickEvent<Button> event) {
        setEditing(true);
    }

    protected void onDone(ClickEvent<Button> event) {
        setEditing(false);
    }

    protected C getEditContentPanel() {
        return editContentPanel;
    }

    protected abstract String getTitle();

    protected abstract C createEditContentPanel();

}
