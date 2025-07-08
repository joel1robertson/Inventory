package org.joro.inventory.service;

import org.joro.inventory.ui.model.data.LinkCategory;

import java.util.List;

public interface LinkCategoryService {

    List<LinkCategory> fetchAll();

    LinkCategory fetch(Long linkCategoryKey);

    LinkCategory save(LinkCategory linkCategory);

    void remove(LinkCategory linkCategory);
}
