package org.joro.inventory.ui.component.editpanel;

import com.vaadin.flow.component.Component;

public abstract class EditFieldContentPanel<T, C extends Component> extends EditContentPanel<C> {

    public abstract T getItem();

    public abstract void setItem(T item);

    public abstract boolean save();

    public abstract void revert();
}
