package org.joro.inventory.cache;

import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.joro.inventory.model.Item;

import java.util.List;

@SpringComponent
@UIScope
public final class UICache {
    private String filterText = "";
    private List<GridSortOrder<Item>> sortOrders = List.of();

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public List<GridSortOrder<Item>> getSortOrders() {
        return sortOrders;
    }

    public void setSortOrders(List<GridSortOrder<Item>> sortOrders) {
        this.sortOrders = sortOrders;
    }
}
