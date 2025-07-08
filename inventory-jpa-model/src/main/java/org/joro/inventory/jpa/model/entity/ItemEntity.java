package org.joro.inventory.jpa.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpa.model.projection.ItemDetailProjection;
import org.joro.inventory.jpa.model.projection.ItemGalleryProjection;
import org.joro.inventory.jpa.model.projection.ItemKeyProjection;
import org.joro.inventory.jpa.model.projection.ItemSummaryProjection;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "item")
public class ItemEntity extends AbstractEntity<Long>
        implements ItemKeyProjection, ItemSummaryProjection, ItemGalleryProjection, ItemDetailProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_key")
    private Long itemKey;

    @ManyToOne
    @JoinColumn(name = "item_ctgry_key")
    private ItemCategoryEntity itemCategory;

    @Column(name = "name_txt")
    private String name;

    @Column(name = "mnfr_name_txt")
    private String manufacturerName;

    @Column(name = "modl_nmbr_txt")
    private String modelNumber;

    @Column(name = "valu_amt")
    private Integer value;

    @Column(name = "askng_amt")
    private Integer askingPrice;

    @Column(name = "qty_amt")
    private Integer quantity;

    @Column(name = "note_txt")
    private String notes;

    @Column(name = "ttle_txt")
    private String title;

    @Column(name = "desc_txt")
    private String description;

    @Column(name = "manual_ind")
    private boolean manual;

    // foreign joins

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemLinkEntity> itemLinks;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImageEntity> itemImages;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemMarketplaceEntity> itemMarketplaces;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemProspectEntity> itemProspects;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSaleEntity> itemSales;


    @Override
    Long getKey() {
        return itemKey;
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
