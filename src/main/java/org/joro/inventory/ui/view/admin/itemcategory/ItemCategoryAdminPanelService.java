package org.joro.inventory.ui.view.admin.itemcategory;

import org.joro.inventory.model.ItemCategory;
import org.joro.inventory.service.ItemCategoryService;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;

import java.util.List;

public class ItemCategoryAdminPanelService implements AdminPanelService<ItemCategory, ItemCategory> {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryAdminPanelService(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public List<ItemCategory> fetchSummaries() {
        return itemCategoryService.fetchAll();
    }

    @Override
    public ItemCategory fetchSummaryFromDetail(ItemCategory itemCategory) {
        return itemCategory;
    }

    @Override
    public ItemCategory fetchDetailFromSummary(ItemCategory itemCategory) {
        return itemCategory;
    }

    @Override
    public ItemCategory createDetail() {
        return new ItemCategory();
    }

    @Override
    public ItemCategory saveDetail(ItemCategory itemCategory) {
        return itemCategoryService.save(itemCategory);
    }

    @Override
    public void remove(ItemCategory itemCategory) {
        itemCategoryService.remove(itemCategory);
    }
}
