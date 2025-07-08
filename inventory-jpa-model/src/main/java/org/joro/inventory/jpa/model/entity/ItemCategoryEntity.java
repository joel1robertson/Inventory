package org.joro.inventory.jpa.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.joro.inventory.jpa.model.projection.ItemCategoryProjection;

@Getter
@Setter
@Entity
@Table(name = "item_ctgry")
public class ItemCategoryEntity extends AbstractEntity<Long> implements ItemCategoryProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_ctgry_key")
    private Long itemCategoryKey;

    @Column(name = "name_txt")
    private String name;

    // foreign joins

//    @OneToMany(mappedBy = "itemCategory", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ItemEntity> items;


    @Override
    Long getKey() {
        return itemCategoryKey;
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
