package org.joro.inventory.jpaservice;

import org.joro.inventory.jpamodel.dto.LinkCategoryDto;
import org.joro.inventory.jparepository.LinkCategoryRepository;
import org.joro.inventory.jpaservice.map.LinkCategoryMap;
import org.joro.inventory.jpaservice.map.MapUtil;
import org.joro.inventory.model.LinkCategory;
import org.joro.inventory.service.LinkCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaLinkCategoryService implements LinkCategoryService {

    private final LinkCategoryRepository repository;

    public JpaLinkCategoryService(LinkCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LinkCategory> fetchAll() {
        var sortedByName = Sort.sort(LinkCategoryDto.class).by(LinkCategoryDto::getName);
        return MapUtil.map(repository.findAll(sortedByName), LinkCategoryMap::map);
    }

    @Override
    public LinkCategory fetch(Long linkCategoryKey) {
        return LinkCategoryMap.map(repository.findByLinkCategoryKey(linkCategoryKey));
    }

    @Override
    public LinkCategory save(LinkCategory linkCategory) {
        return LinkCategoryMap.map(repository.saveAndFlush(LinkCategoryMap.map(linkCategory)));
    }

    @Override
    public void remove(LinkCategory linkCategory) {
        repository.delete(LinkCategoryMap.map(linkCategory));
    }
}
