package org.joro.inventory.service;

import org.joro.inventory.ui.model.data.ItemCategory;

import java.util.List;

public interface ItemCategoryService {

    List<ItemCategory> fetchAll();

    ItemCategory fetch(Long itemCategoryKey);

    ItemCategory save(ItemCategory itemCategory);

    void remove(ItemCategory itemCategory);
}
