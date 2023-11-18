package org.joro.inventory.jpaservice;

import org.joro.inventory.jpamodel.dto.ItemCategoryDto;
import org.joro.inventory.jparepository.ItemCategoryRepository;
import org.joro.inventory.jpaservice.map.ItemCategoryMap;
import org.joro.inventory.jpaservice.map.MapUtil;
import org.joro.inventory.model.ItemCategory;
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
        var sortedByName = Sort.sort(ItemCategoryDto.class).by(ItemCategoryDto::getName);
        return MapUtil.map(repository.findAll(sortedByName), ItemCategoryMap::map);
    }

    @Override
    public ItemCategory fetch(Long itemCategoryKey) {
        return ItemCategoryMap.map(repository.findByItemCategoryKey(itemCategoryKey));
    }

    @Override
    public ItemCategory save(ItemCategory itemCategory) {
        return ItemCategoryMap.map(repository.saveAndFlush(ItemCategoryMap.map(itemCategory)));
    }

    @Override
    public void remove(ItemCategory itemCategory) {
        repository.delete(ItemCategoryMap.map(itemCategory));
    }
}
