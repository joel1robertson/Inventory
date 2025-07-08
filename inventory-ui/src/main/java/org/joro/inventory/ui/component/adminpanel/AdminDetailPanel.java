package org.joro.inventory.ui.component.adminpanel;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;
import org.joro.inventory.ui.component.editpanel.BinderFieldContentPanel;
import org.joro.inventory.ui.component.editpanel.EditDiscardSavePanel;

public abstract class AdminDetailPanel<D, C extends Component>
        extends EditDiscardSavePanel<D, BinderFieldContentPanel<D, C>> {

    @Override
    public void setEditing(boolean editing) {
        super.setEditing(editing);
        fireEditingChangeEvent(true);
    }

    @Override
    protected void onSave(ClickEvent<Button> event) {
        super.onSave(event);
        fireSaveEvent(event.isFromClient());
    }

    // EditingChangeEvent

    /**
     * The event fired when the admin detail panel's editing is changed.
     */
    public static class EditingChangeEvent<D, C extends Component> extends ComponentEvent<AdminDetailPanel<D,C>> {
        /**
         * Create an editing change event.
         *
         * @param source the admin detail panel
         * @param fromClient {@code true} if the event originated from a client, {@code false} otherwise
         */
        public EditingChangeEvent(AdminDetailPanel<D,C> source, boolean fromClient) {
            super(source, fromClient);
        }

        public boolean isEditing() {
            return getSource().isEditing();
        }
    }

    /**
     * Add an editing change listener to the admin detail panel.
     *
     * @param editingChangeListener a listener to call when the admin detail panel's editing is changed
     * @return a registration to facilitate removal of the editing change listener
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Registration addEditingChangeListener(ComponentEventListener<EditingChangeEvent<D,C>> editingChangeListener) {
        return addListener(EditingChangeEvent.class, (ComponentEventListener) editingChangeListener);
    }

    @SuppressWarnings("java:S3398")  // not moving to inner class to maintain pattern
    private void fireEditingChangeEvent(boolean fromClient) {
        fireEvent(new EditingChangeEvent<>(this, fromClient));
    }

    // SaveEvent

    /**
     * The event fired when the admin detail panel is saved.
     */
    public static class SaveEvent<D, C extends Component> extends ComponentEvent<AdminDetailPanel<D,C>> {
        /**
         * Create a save event.
         *
         * @param source the admin detail panel
         * @param fromClient {@code true} if the event originated from a client, {@code false} otherwise
         */
        public SaveEvent(AdminDetailPanel<D,C> source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    /**
     * Add a save listener to the admin detail panel.
     *
     * @param saveListener a listener to call when the admin detail panel is saved
     * @return a registration to facilitate removal of the editing change listener
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Registration addSaveListener(ComponentEventListener<SaveEvent<D,C>> saveListener) {
        return addListener(SaveEvent.class, (ComponentEventListener) saveListener);
    }

    @SuppressWarnings("java:S3398")  // not moving to inner class to maintain pattern
    private void fireSaveEvent(boolean fromClient) {
        fireEvent(new SaveEvent<>(this, fromClient));
    }
}
