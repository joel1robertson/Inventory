package org.joro.inventory.jpaservice;

import org.joro.inventory.jpamodel.dto.ItemDetailDto;
import org.joro.inventory.jparepository.ItemRepository;
import org.joro.inventory.jpaservice.map.ItemMap;
import org.joro.inventory.jpaservice.map.MapUtil;
import org.joro.inventory.model.Item;
import org.joro.inventory.service.ItemService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaItemService implements ItemService {

    private final ItemRepository repository;

    public JpaItemService(ItemRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public List<Item> fetchAll() {
//        var sortedByName = Sort.sort(ItemSummaryDto.class).by(ItemSummaryDto::getName);
//        return MapUtil.map(repository.findAll(sortedByName), ItemMap::map);
//    }
    @Override
    public List<Item> fetchAll() {
        var sortedByName = Sort.sort(ItemDetailDto.class).by(ItemDetailDto::getName);
        return MapUtil.map(repository.findAll(sortedByName), ItemMap::map);
    }

    @Override
    public Item fetch(Long itemKey) {
        return ItemMap.map(repository.findByItemKey(itemKey));
    }

    @Override
    public Item save(Item item) {
        return ItemMap.map(repository.saveAndFlush(ItemMap.map(item)));
    }

    @Override
    public void remove(Item item) {
        repository.delete(ItemMap.map(item));
    }

    @Override
    public Item fetchGalleryFor(Long itemKey) {
        return ItemMap.map(repository.findItemGalleryDtoByItemKey(itemKey));
    }

}
