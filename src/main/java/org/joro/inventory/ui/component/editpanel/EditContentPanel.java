package org.joro.inventory.ui.component.editpanel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;

public abstract class EditContentPanel<C extends Component> extends Composite<C>
        implements HasStyle, HasSize {

    public abstract boolean isEditing();

    public abstract void setEditing(boolean editing);
}
