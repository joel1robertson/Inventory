package org.joro.inventory.jpamodel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpamodel.dto.MarketplaceDto;

@Getter
@Setter
@Entity
@Table(name = "mktplc")
public class MarketplaceEntity extends AbstractEntity<Long> implements MarketplaceDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mktplc_key")
    private Long marketplaceKey;

    @Column(name = "name_txt")
    private String name;

    @Column(name = "site_url_txt")
    private String siteUrl;

    // foreign joins

//    @OneToMany(mappedBy = "marketplace", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ItemMarketplaceEntity> itemMarketplaces;


    @Override
    Long getKey() {
        return marketplaceKey;
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
