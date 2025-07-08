package org.joro.inventory.jpa.service;

import org.joro.inventory.jpa.model.projection.LinkCategoryProjection;
import org.joro.inventory.jpa.client.LinkCategoryRepository;
import org.joro.inventory.jpa.service.mapper.LinkCategoryMapper;
import org.joro.inventory.jpa.service.mapper.MapperUtil;
import org.joro.inventory.ui.model.data.LinkCategory;
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
        var sortedByName = Sort.sort(LinkCategoryProjection.class).by(LinkCategoryProjection::getName);
        return MapperUtil.map(repository.findAll(sortedByName), LinkCategoryMapper::map);
    }

    @Override
    public LinkCategory fetch(Long linkCategoryKey) {
        return LinkCategoryMapper.map(repository.findByLinkCategoryKey(linkCategoryKey));
    }

    @Override
    public LinkCategory save(LinkCategory linkCategory) {
        return LinkCategoryMapper.map(repository.saveAndFlush(LinkCategoryMapper.map(linkCategory)));
    }

    @Override
    public void remove(LinkCategory linkCategory) {
        repository.delete(LinkCategoryMapper.map(linkCategory));
    }
}
