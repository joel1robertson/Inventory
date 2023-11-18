package org.joro.inventory.jpaservice;

import org.joro.inventory.jpamodel.dto.ProspectDto;
import org.joro.inventory.jparepository.ProspectRepository;
import org.joro.inventory.jpaservice.map.MapUtil;
import org.joro.inventory.jpaservice.map.ProspectMap;
import org.joro.inventory.model.Prospect;
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
        var sortedByName = Sort.sort(ProspectDto.class).by(ProspectDto::getName);
        return MapUtil.map(repository.findAll(sortedByName), ProspectMap::map);
    }

    @Override
    public Prospect fetch(Long prospectKey) {
        return ProspectMap.map(repository.findByProspectKey(prospectKey));
    }

    @Override
    public Prospect save(Prospect prospect) {
        return ProspectMap.map(repository.saveAndFlush(ProspectMap.map(prospect)));
    }

    @Override
    public void remove(Prospect prospect) {
        repository.delete(ProspectMap.map(prospect));
    }
}
