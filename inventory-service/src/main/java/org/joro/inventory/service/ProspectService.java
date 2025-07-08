package org.joro.inventory.service;

import org.joro.inventory.ui.model.data.Prospect;

import java.util.List;

public interface ProspectService {

    List<Prospect> fetchAll();

    Prospect fetch(Long prospectKey);

    Prospect save(Prospect prospect);

    void remove(Prospect prospect);
}
