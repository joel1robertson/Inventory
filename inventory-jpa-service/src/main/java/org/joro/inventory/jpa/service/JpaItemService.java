package org.joro.inventory.jpa.service;

import org.joro.inventory.jpa.model.projection.ItemDetailProjection;
import org.joro.inventory.jpa.client.ItemRepository;
import org.joro.inventory.jpa.service.mapper.ItemMapper;
import org.joro.inventory.jpa.service.mapper.MapperUtil;
import org.joro.inventory.ui.model.data.Item;
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
//        var sortedByName = Sort.sort(ItemSummaryProjection.class).by(ItemSummaryProjection::getName);
//        return MapperUtil.map(repository.findAll(sortedByName), ItemMapper::map);
//    }
    @Override
    public List<Item> fetchAll() {
        var sortedByName = Sort.sort(ItemDetailProjection.class).by(ItemDetailProjection::getName);
        return MapperUtil.map(repository.findAll(sortedByName), ItemMapper::map);
    }

    @Override
    public Item fetch(Long itemKey) {
        return ItemMapper.map(repository.findByItemKey(itemKey));
    }

    @Override
    public Item save(Item item) {
        return ItemMapper.map(repository.saveAndFlush(ItemMapper.map(item)));
    }

    @Override
    public void remove(Item item) {
        repository.delete(ItemMapper.map(item));
    }

    @Override
    public Item fetchGalleryFor(Long itemKey) {
        return ItemMapper.map(repository.findItemGalleryProjectionByItemKey(itemKey));
    }

}
