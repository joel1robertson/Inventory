package org.joro.inventory.cache;

import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.ui.model.data.Item;

import java.util.List;

@Getter
@Setter
@SpringComponent
@UIScope
public final class UICache {
    private String filterText = "";
    private List<GridSortOrder<Item>> sortOrders = List.of();
}
