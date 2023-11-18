package org.joro.inventory.jparepository;

import org.joro.inventory.jpamodel.dto.LinkCategoryDto;
import org.joro.inventory.jpamodel.entity.LinkCategoryEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LinkCategoryRepository extends LongKeyRepository<LinkCategoryEntity> {

    List<LinkCategoryDto> findAll(Sort sort);

    LinkCategoryDto findByLinkCategoryKey(Long linkCategoryKey);

    LinkCategoryDto saveAndFlush(LinkCategoryEntity linkCategoryEntity);

    void delete(LinkCategoryEntity linkCategoryEntity);
}
