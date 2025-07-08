package org.joro.inventory.jpa.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpa.model.projection.ItemLinkProjection;

@Getter
@Setter
@Entity
@Table(name = "item_link")
public class ItemLinkEntity extends AbstractEntity<Long> implements ItemLinkProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_link_key")
    private Long itemLinkKey;

    @ManyToOne
    @JoinColumn(name = "item_key")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "link_ctgry_key")
    private LinkCategoryEntity linkCategory;

    @Column(name = "cptn_txt")
    private String caption;

    @Column(name = "href_txt")
    private String href;

    @Column(name = "sqnc_idx")
    private String sequenceIndex;


    @Override
    Long getKey() {
        return itemLinkKey;
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
