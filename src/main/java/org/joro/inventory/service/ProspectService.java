package org.joro.inventory.service;

import org.joro.inventory.model.Prospect;

import java.util.List;

public interface ProspectService {

    List<Prospect> fetchAll();

    Prospect fetch(Long prospectKey);

    Prospect save(Prospect prospect);

    void remove(Prospect prospect);
}
