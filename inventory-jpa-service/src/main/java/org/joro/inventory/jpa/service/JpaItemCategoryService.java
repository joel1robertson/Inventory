package org.joro.inventory.jpa.service;

import org.joro.inventory.jpa.model.projection.ItemCategoryProjection;
import org.joro.inventory.jpa.client.ItemCategoryRepository;
import org.joro.inventory.jpa.service.mapper.ItemCategoryMapper;
import org.joro.inventory.jpa.service.mapper.MapperUtil;
import org.joro.inventory.ui.model.data.ItemCategory;
import org.joro.inventory.service.ItemCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaItemCategoryService implements ItemCategoryService {

    private final ItemCategoryRepository repository;

    public JpaItemCategoryService(ItemCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ItemCategory> fetchAll() {
        var sortedByName = Sort.sort(ItemCategoryProjection.class).by(ItemCategoryProjection::getName);
        return MapperUtil.map(repository.findAll(sortedByName), ItemCategoryMapper::map);
    }

    @Override
    public ItemCategory fetch(Long itemCategoryKey) {
        return ItemCategoryMapper.map(repository.findByItemCategoryKey(itemCategoryKey));
    }

    @Override
    public ItemCategory save(ItemCategory itemCategory) {
        return ItemCategoryMapper.map(repository.saveAndFlush(ItemCategoryMapper.map(itemCategory)));
    }

    @Override
    public void remove(ItemCategory itemCategory) {
        repository.delete(ItemCategoryMapper.map(itemCategory));
    }
}
