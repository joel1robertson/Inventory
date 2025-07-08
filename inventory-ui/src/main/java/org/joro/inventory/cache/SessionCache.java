package org.joro.inventory.cache;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.joro.inventory.ui.model.data.Item;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringComponent
@VaadinSessionScope
public final class SessionCache {
    private List<Item> filteredViewItems = List.of();

    public List<Item> getFilteredViewItems() {
        return Collections.unmodifiableList(Optional.ofNullable(this.filteredViewItems).orElse(List.of()));
    }

    public void setFilteredViewItems(List<Item> filteredViewItems) {
        this.filteredViewItems = filteredViewItems;
    }
}
