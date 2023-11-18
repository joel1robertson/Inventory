package org.joro.inventory.ui.component.editpanel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;

public abstract class BinderFieldContentPanel<T, C extends Component> extends EditFieldContentPanel<T, C> {
    private transient T item;
    private final Binder<T> binder;
    private boolean editing;

    protected BinderFieldContentPanel() {
        binder = new Binder<>();
        setEditing(false);
    }

    public <V> Binder.BindingBuilder<T, V> forField(HasValue<?, V> field) {
        return binder.forField(field);
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void setItem(T item) {
        this.item = item;
        binder.readBean(item);
    }

    @Override
    public boolean isEditing() {
        return editing;
    }

    @Override
    public void setEditing(boolean editing) {
        this.editing = editing;
        binder.setReadOnly(!editing);
    }

    @Override
    public boolean save() {
        return binder.writeBeanIfValid(item);
    }

    @Override
    public void revert() {
        binder.readBean(item);
    }
}
