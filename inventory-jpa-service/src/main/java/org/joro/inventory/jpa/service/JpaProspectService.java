package org.joro.inventory.jpa.service;

import org.joro.inventory.jpa.model.projection.ProspectProjection;
import org.joro.inventory.jpa.client.ProspectRepository;
import org.joro.inventory.jpa.service.mapper.MapperUtil;
import org.joro.inventory.jpa.service.mapper.ProspectMapper;
import org.joro.inventory.ui.model.data.Prospect;
import org.joro.inventory.service.ProspectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaProspectService implements ProspectService {

    private final ProspectRepository repository;

    public JpaProspectService(ProspectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Prospect> fetchAll() {
        var sortedByName = Sort.sort(ProspectProjection.class).by(ProspectProjection::getName);
        return MapperUtil.map(repository.findAll(sortedByName), ProspectMapper::map);
    }

    @Override
    public Prospect fetch(Long prospectKey) {
        return ProspectMapper.map(repository.findByProspectKey(prospectKey));
    }

    @Override
    public Prospect save(Prospect prospect) {
        return ProspectMapper.map(repository.saveAndFlush(ProspectMapper.map(prospect)));
    }

    @Override
    public void remove(Prospect prospect) {
        repository.delete(ProspectMapper.map(prospect));
    }
}
