package org.joro.inventory.service;

import org.joro.inventory.ui.model.data.Item;

import java.util.List;

public interface ItemService {

    List<Item> fetchAll();

    Item fetch(Long itemKey);

    Item save(Item item);

    void remove(Item item);

    Item fetchGalleryFor(Long itemKey);
}
