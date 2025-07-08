package org.joro.inventory.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.shared.HasClearButton;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.theme.lumo.LumoIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * <pre>
 * +-content(FlexLayout)-----------------------------------------+
 * | +-headerBar(FlexLayout)-----------------------------------+ |
 * | | +-labelSlot(Label)-+                      +-addButton-+ | |
 * | | |                  |                      |     +     | | |
 * | | +------------------+                      +-----------+ | |
 * | +---------------------------------------------------------+ |
 * | +-grid----------------------------------------------------+ |
 * | |                                                         | |
 * | |                                                         | |
 * | |                                                         | |
 * | +---------------------------------------------------------+ |
 * | +-helperSlot(Div)-----------------------------------------+ |
 * | |                                                         | |
 * | +---------------------------------------------------------+ |
 * | +-errorMessageSlot(Div)-----------------------------------+ |
 * | |                                                         | |
 * | +---------------------------------------------------------+ |
 * +-------------------------------------------------------------+
 *
 * </pre>
 * @param <T>
 */
public class GridField<T> extends AbstractCompositeField<FlexLayout, GridField<T>, List<T>>
    implements HasSize, HasLabel, HasHelper, HasValidation {

    private transient Supplier<T> itemFactory;

    private final NativeLabel labelSlot;
    private final Button addButton;
    private final Grid<T> grid;
    private final Grid.Column<T> buttonColumn;
    private final Div helperSlot;
    private final Div errorMessageSlot;

    private final Editor<T> gridEditor;
    private final Binder<T> gridBinder;
    private boolean invalid;

    public GridField() {
        super(List.of());

        addClassNames("grid-field");

        labelSlot = new NativeLabel();
        labelSlot.getElement().setAttribute("part", "label");

        addButton = new Button(LumoIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        addButton.addClickListener(this::onAddGridItem);

        var headerBar = new FlexLayout();
        headerBar.setWidthFull();
        headerBar.setFlexDirection(FlexLayout.FlexDirection.ROW);
        headerBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerBar.add(labelSlot);
        headerBar.add(addButton);

        grid = new Grid<>();
        grid.setId("grid-field");
        grid.getElement().setAttribute("part", "input");
        grid.setHeightFull();
        grid.setWidthFull();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.setColumnReorderingAllowed(true);
        grid.setMultiSort(true);
        buttonColumn = grid.addColumn(buttonColumnRenderer(
                        this::onEditGridItem,
                        this::onConfirmRemoveGridItem,
                        this::onSaveGridItem,
                        this::onRevertGridItem))
                .setWidth("60px")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFrozenToEnd(true);

        helperSlot = new Div();
        helperSlot.getElement().setAttribute("part", "helper");
        helperSlot.setWidthFull();

        errorMessageSlot = new Div();
        errorMessageSlot.getElement().setAttribute("part", "error-message");
        errorMessageSlot.setWidthFull();

        var content = getContent();
        content.setMinHeight(20, Unit.EX);
        content.add(headerBar);
        content.add(grid);
        content.add(helperSlot);
        content.add(errorMessageSlot);

        labelSlot.setFor(grid);

        gridBinder = new Binder<>();

        gridEditor = grid.getEditor();
        gridEditor.setBuffered(true);
        gridEditor.setBinder(gridBinder);
    }

    public GridField(String label) {
        this();

        setLabel(label);
    }

    public GridField(Supplier<T> itemFactory) {
        this();

        this.itemFactory = itemFactory;
    }

    public GridField(String label, Supplier<T> itemFactory) {
        this(label);

        this.itemFactory = itemFactory;
    }

    @Override
    public String getLabel() {
        return labelSlot.getText();
    }

    @Override
    public void setLabel(String label) {
        labelSlot.setText(label);
        getElement().setAttribute("has-label",
                !Optional.ofNullable(label).map(String::isEmpty).orElse(true));
    }

    @Override
    public String getHelperText() {
        return helperSlot.getText();
    }

    @Override
    public void setHelperText(String helperText) {
        helperSlot.setText(helperText);
    }

    @Override
    public Component getHelperComponent() {
        return helperSlot.getChildren().findFirst().orElse(null);
    }

    @Override
    public void setHelperComponent(Component component) {
        helperSlot.removeAll();
        helperSlot.add(component);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        errorMessageSlot.setText(errorMessage);
    }

    @Override
    public String getErrorMessage() {
        return errorMessageSlot.getChildren()
                .findFirst()
                .filter(Text.class::isInstance)
                .map(Text.class::cast)
                .map(Text::getText)
                .orElse(null);
    }

    @Override
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    @Override
    public boolean isInvalid() {
        return invalid;
    }

    public <V, C extends Component & HasValue<?, V>> Grid.Column<T> addColumn(ValueProvider<T, V> getter, Setter<T, V> setter,
                                                                              C editorComponent) {
        if (editorComponent instanceof HasSize hasSize) {
            hasSize.setWidthFull();
        }
        if (editorComponent instanceof HasClearButton hasClearButton) {
            hasClearButton.setClearButtonVisible(true);
        }

        gridBinder.forField(editorComponent).bind(getter, setter);

        var column = grid.addColumn(getter).setEditorComponent(editorComponent);
        placeBeforeButtonColumn(column);
        return column;
    }

    public <V, C extends Component & HasValue<?, V>> Grid.Column<T> addColumn(Renderer<T> renderer,
                                                                              ValueProvider<T, V> getter, Setter<T, V> setter,
                                                                              C editorComponent) {
        if (editorComponent instanceof HasSize hasSize) {
            hasSize.setWidthFull();
        }
        if (editorComponent instanceof HasClearButton hasClearButton) {
            hasClearButton.setClearButtonVisible(true);
        }

        gridBinder.forField(editorComponent).bind(getter, setter);

        var column = grid.addColumn(renderer).setEditorComponent(editorComponent);
        placeBeforeButtonColumn(column);
        return column;
    }

    private void placeBeforeButtonColumn(Grid.Column<T> column) {
        var columns = new ArrayList<>(grid.getColumns());
        columns.remove(column);
        columns.add(columns.size() - 1, column);
        grid.setColumnOrder(columns);
    }

    @Override
    protected void setPresentationValue(List<T> newPresentationValue) {
        grid.setItems(Optional.ofNullable(newPresentationValue).orElse(List.of()));
    }

    private Renderer<T> buttonColumnRenderer(Consumer<T> editButtonClickHandler,
                                             Consumer<T> removeButtonClickHandler,
                                             Consumer<T> saveButtonClickHandler,
                                             Consumer<T> revertButtonClickHandler) {
        return LitRenderer.<T>of("""
                    <vaadin-button title="Edit" theme="tertiary-inline" ?hidden=${item.isEditMode}
                     @click=${onEditClick}>
                        <vaadin-icon icon="lumo:edit">
                    </vaadin-button>
                    <vaadin-button title="Save" theme="tertiary-inline" ?hidden=${!item.isEditMode}
                     @click=${onSaveClick}>
                        <vaadin-icon icon="lumo:checkmark">
                    </vaadin-button>
                    <vaadin-button title="Revert" theme="tertiary-inline" ?hidden=${!item.isEditMode}
                     @click=${onRevertClick}>
                        <vaadin-icon icon="lumo:undo" style="color: var(--lumo-contrast-70pct);">
                    </vaadin-button>
                    <vaadin-button title="Remove" theme="tertiary-inline" ?hidden=${item.isEditMode}
                     @click=${onRemoveClick}>
                        <vaadin-icon icon="lumo:cross" style="color: var(--lumo-error-color);">
                    </vaadin-button>
                """)
                .withProperty("isEditMode", item -> item.equals(grid.getEditor().getItem()))
                .withFunction("onEditClick", editButtonClickHandler::accept)
                .withFunction("onRemoveClick", removeButtonClickHandler::accept)
                .withFunction("onSaveClick", saveButtonClickHandler::accept)
                .withFunction("onRevertClick", revertButtonClickHandler::accept);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        if (gridEditor.isOpen()) {
            gridEditor.cancel();
        }
        updateButtonVisibility();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        grid.setEnabled(enabled);
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        var visible = isEnabled() && !isReadOnly() && (itemFactory != null);
        addButton.setVisible(visible);
        buttonColumn.setVisible(visible);
    }

    private void onAddGridItem(ClickEvent<Button> event) {
        T newItem = itemFactory.get();

        var items = new ArrayList<>(getValue());
        items.add(newItem);

        setValue(items);

        if (gridEditor.isOpen()) {
            gridEditor.cancel();
        }
        gridEditor.editItem(newItem);

        grid.scrollToEnd();
    }

    private void onEditGridItem(T item) {
        if (gridEditor.isOpen()) {
            gridEditor.cancel();
        }
        gridEditor.editItem(item);
    }

    private void onConfirmRemoveGridItem(T item) {
        var label = getLabel();
        var removeGridItemDialog = new ConfirmDialog("Remove %s Item".formatted(label),
                "Are you sure you want to remove this %s item (%s)?".formatted(label.toLowerCase(), item),
                "Remove", event -> onRemoveGridItem(item));
        removeGridItemDialog.setConfirmButtonTheme("primary error");
        removeGridItemDialog.setCancelText("Keep");
        removeGridItemDialog.setCancelable(true);
        removeGridItemDialog.open();
    }

    private void onRemoveGridItem(T item) {
        var items = new ArrayList<>(getValue());
        items.remove(item);
        setValue(items);
    }

    private void onSaveGridItem(T item) {
        gridEditor.save();
    }

    private void onRevertGridItem(T item) {
        gridEditor.cancel();
        gridEditor.refresh();
    }

    public boolean isEditing() {
        return gridEditor.isOpen();
    }
}
