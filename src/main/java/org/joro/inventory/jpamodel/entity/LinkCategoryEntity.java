package org.joro.inventory.jpamodel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpamodel.dto.LinkCategoryDto;

@Getter
@Setter
@Entity
@Table(name = "link_ctgry")
public class LinkCategoryEntity extends AbstractEntity<Long> implements LinkCategoryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_ctgry_key")
    private Long linkCategoryKey;

    @Column(name = "name_txt")
    private String name;

    // foreign joins

//    @OneToMany(mappedBy = "linkCategory", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ItemLinkEntity> itemLinks;


    @Override
    Long getKey() {
        return linkCategoryKey;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
