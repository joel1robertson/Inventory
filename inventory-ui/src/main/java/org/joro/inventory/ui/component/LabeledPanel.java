package org.joro.inventory.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;

public class LabeledPanel extends Composite<Div> implements HasStyle, HasSize {

    private final Div labelPart;
    private final Div contentPart;

    public LabeledPanel(Component labelComponent, Component contentComponent) {
        addClassNames("labeled-panel");

        labelPart = new Div();
        labelPart.getElement().setAttribute("part", "label");

        contentPart = new Div();
        contentPart.setSizeFull();
        contentPart.getElement().setAttribute("part", "content");

        var content = getContent();
        content.add(labelPart);
        content.add(contentPart);

        setLabel(labelComponent);
        setContent(contentComponent);
    }

    public LabeledPanel(String label, Component contentComponent) {
        this(new Text(label), contentComponent);
    }

    public LabeledPanel(Component labelComponent) {
        this(labelComponent, null);
    }

    public LabeledPanel(String label) {
        this(label, null);
    }

    public void setLabel(Component labelComponent) {
        labelPart.removeAll();
        if (labelComponent != null) {
            labelPart.add(labelComponent);
        }
    }

    public void setLabel(String label) {
        labelPart.removeAll();
        if (label != null) {
            labelPart.add(new Text(label));
        }
    }

    public void setContent(Component contentComponent) {
        contentPart.removeAll();
        if (contentComponent != null) {
            contentPart.add(contentComponent);
        }
    }

}
