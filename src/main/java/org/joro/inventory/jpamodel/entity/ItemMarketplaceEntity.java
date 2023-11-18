package org.joro.inventory.jpamodel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpamodel.dto.ItemMarketplaceDto;

@Getter
@Setter
@Entity
@Table(name = "item_mktplc")
public class ItemMarketplaceEntity extends AbstractEntity<Long> implements ItemMarketplaceDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_mktplc_key")
    private Long itemMarketplaceKey;

    @ManyToOne
    @JoinColumn(name = "item_key")
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "mktplc_key")
    private MarketplaceEntity marketplace;

    @Column(name = "pstg_url_txt")
    private String postingUrl;


    @Override
    Long getKey() {
        return itemMarketplaceKey;
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
