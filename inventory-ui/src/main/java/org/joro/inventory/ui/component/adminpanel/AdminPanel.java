package org.joro.inventory.ui.component.adminpanel;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @param <S> Summary type
 * @param <D> Detail type, extends summary type
 *
 * <pre>
 * +-content(SplitLayout)------------------------------------------+
 * | +-summaryBlock(VL)-+ +-adminDetailPanel---------------------+ |
 * | |   +-newButton--+ | |                                      | |
 * | |   |     +      | | |                                      | |
 * | |   +------------+ | |                                      | |
 * | | +-summaryGrid--+ | |                                      | |
 * | | | item 1   (-) | | |                                      | |
 * | | | item 2   (-) | | |                                      | |
 * | | | item 3   (-) | | |                                      | |
 * | | | item 4   (-) | | |                                      | |
 * | | | item 5   (-) | | |                                      | |
 * | | | item 6   (-) | | |                                      | |
 * | | | item 7   (-) | | |                                      | |
 * | | | item 8   (-) | | |                                      | |
 * | | |              | | |                                      | |
 * | | |              | | |                                      | |
 * | | +--------------+ | |                                      | |
 * | +------------------+ +--------------------------------------+ |
 * +---------------------------------------------------------------+
 * </pre>
 */
public abstract class AdminPanel<S, D extends S> extends Composite<SplitLayout>
        implements HasStyle, HasSize {
    private final Grid<S> summaryGrid;

    private final transient Supplier<AdminPanelService<S, D>> adminPanelServiceProvider;
    private final ValueProvider<S, String> summaryItemValueProvider;
    private final AdminDetailPanel<D, ?> adminDetailPanel;

    protected AdminPanel() {
        adminPanelServiceProvider = getAdminPanelServiceProvider();
        summaryItemValueProvider = getSummaryItemValueProvider();

        var newButton = new Button(VaadinIcon.PLUS.create());
        newButton.addClickListener(this::onNewClick);

        summaryGrid = new Grid<>();
        summaryGrid.setSizeFull();
        summaryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        summaryGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        summaryGrid.asSingleSelect().addValueChangeListener(this::onSummarySelectionChange);
        summaryGrid.addColumn(this::renderSummaryItem)
                .setSortable(true)
                .setResizable(true)
                .setHeader(getSummaryColumnHeader());
        summaryGrid.addColumn(removeButtonColumnRenderer(this::onConfirmRemoveGridItem))
                .setWidth("42px")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFrozenToEnd(true);

        var summaryBlock = new VerticalLayout();
        summaryBlock.setHeightFull();
        summaryBlock.setAlignItems(FlexComponent.Alignment.END);
        summaryBlock.add(newButton);
        summaryBlock.add(summaryGrid);

        adminDetailPanel = createAdminDetailPanel();
        adminDetailPanel.setSizeFull();
        adminDetailPanel.addEditingChangeListener(this::onEditing);
        adminDetailPanel.addSaveListener(this::onSave);

        var content = getContent();
        content.setSplitterPosition(25);
        content.addToPrimary(summaryBlock);
        content.addToSecondary(adminDetailPanel);
    }

    private String renderSummaryItem(S s) {
        return summaryItemValueProvider.apply(s);
    }

    private Renderer<S> removeButtonColumnRenderer(Consumer<S> removeButtonClickHandler) {
        return LitRenderer.<S>of("""
                    <vaadin-button title="Remove" theme="tertiary-inline"
                     ?hidden=${item.isEditMode} @click=${onRemoveClick}>
                        <vaadin-icon icon="lumo:cross" style="color: var(--lumo-error-color);">
                    </vaadin-button>
                """)
                .withFunction("onRemoveClick", removeButtonClickHandler::accept);
    }

    private void onSummarySelectionChange(ComponentValueChangeEvent<Grid<S>,S> event) {
        var summary = event.getValue();
        adminDetailPanel.setItem(adminPanelServiceProvider.get().fetchDetailFromSummary(summary));
        onSummaryItemSelectionChange(summary);
    }

    private void onEditing(AdminDetailPanel.EditingChangeEvent<D,?> event) {
        summaryGrid.setEnabled(!event.isEditing());
    }

    private void onSave(AdminDetailPanel.SaveEvent<D,?> event) {
        var adminPanelService = adminPanelServiceProvider.get();
        var detail = adminPanelService.saveDetail(event.getSource().getItem());
        populate();
        selectItem(adminPanelService.fetchSummaryFromDetail(detail));
    }

    private void onNewClick(ClickEvent<Button> event) {
        var detailItem = adminPanelServiceProvider.get().createDetail();
        adminDetailPanel.setItem(detailItem);
        adminDetailPanel.setEditing(true);
    }

    private void onConfirmRemoveGridItem(S summary) {
        var summaryColumnHeader = getSummaryColumnHeader();
        var removeGridItemDialog = new ConfirmDialog("Remove %s Item".formatted(summaryColumnHeader),
                "Are you sure you want to remove this %s item (%s)?".formatted(summaryColumnHeader.toLowerCase(),
                        getSummaryItemValueProvider().apply(summary)),
                "Remove", event -> onRemoveGridItem(summary));
        removeGridItemDialog.setConfirmButtonTheme("primary error");
        removeGridItemDialog.setCancelText("Keep");
        removeGridItemDialog.setCancelable(true);
        removeGridItemDialog.open();
    }

    private void onRemoveGridItem(S summary) {
        adminPanelServiceProvider.get().remove(summary);
        selectItem(null);
        populate();
    }

    protected void populate() {
        var selectedItem = summaryGrid.asSingleSelect().getValue();
        summaryGrid.setItems(adminPanelServiceProvider.get().fetchSummaries());
        selectItem(selectedItem);
    }

    protected void selectItem(S summary) {
        summaryGrid.asSingleSelect().setValue(summary);
    }

    protected abstract Supplier<AdminPanelService<S,D>> getAdminPanelServiceProvider();

    protected abstract String getSummaryColumnHeader();

    protected abstract ValueProvider<S, String> getSummaryItemValueProvider();

    protected abstract <C extends Component> AdminDetailPanel<D, C> createAdminDetailPanel();

    protected abstract void onSummaryItemSelectionChange(S summary);
}
