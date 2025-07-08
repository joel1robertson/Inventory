package org.joro.inventory.jpa.client;

import org.joro.inventory.jpa.model.projection.LinkCategoryProjection;
import org.joro.inventory.jpa.model.entity.LinkCategoryEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LinkCategoryRepository extends LongKeyRepository<LinkCategoryEntity> {

    List<LinkCategoryProjection> findAll(Sort sort);

    LinkCategoryProjection findByLinkCategoryKey(Long linkCategoryKey);

    LinkCategoryProjection saveAndFlush(LinkCategoryEntity linkCategoryEntity);

    void delete(LinkCategoryEntity linkCategoryEntity);
}
