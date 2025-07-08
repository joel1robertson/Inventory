package org.joro.inventory.jpa.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpa.model.projection.ItemImageProjection;

@Getter
@Setter
@Entity
@Table(name = "item_img")
public class ItemImageEntity extends AbstractEntity<Long> implements ItemImageProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_key")
    private Long itemImageKey;

    @ManyToOne
    @JoinColumn(name = "item_key")
    private ItemEntity item;

    @Column(name = "cptn_txt")
    private String caption;

    @Column(name = "cntnt_typ_txt")
    private String contentType;

    @Column(name = "img_data")
    private byte[] imageData;

    @Column(name = "sqnc_idx")
    private String sequenceIndex;


    @Override
    Long getKey() {
        return itemImageKey;
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
