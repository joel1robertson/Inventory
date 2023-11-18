package org.joro.inventory.service;

import org.joro.inventory.model.ItemCategory;

import java.util.List;

public interface ItemCategoryService {

    List<ItemCategory> fetchAll();

    ItemCategory fetch(Long itemCategoryKey);

    ItemCategory save(ItemCategory itemCategory);

    void remove(ItemCategory itemCategory);
}
